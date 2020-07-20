package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.DataDeuce;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.battle.participant.BattleClientAPI;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.subjects.SingleSubject;

public class BaseBattle implements Battle, BattleClientAPI {

    private final AtomicBoolean isActive = new AtomicBoolean(false);

    private final Map<BattleParticipant, List<BattleParticipant>> opponents = new HashMap<>();
    private final BattleParticipant user;
    private final List<BattleParticipant> userSide;
    private final List<BattleParticipant> enemySide;

    private final SingleSubject<ReadableBattleSummaryData> finishBattleObservable;

    private final BattleEventsHandler battleEvents;
    private final BattleStartMode starter;

    private double battleTime;

    public BaseBattle(final BattleParticipant user,
               final List<BattleParticipant> userSide,
               final List<BattleParticipant> enemySide) {
        this(user, userSide, enemySide, BattleStartMode.DEFAULT, 0);
    }

    public BaseBattle(final BattleParticipant user,
               final List<BattleParticipant> userSide,
               final List<BattleParticipant> enemySide,
               final BattleStartMode starter,
               final double startTime) {
        this.user = user;
        this.userSide = userSide;
        this.enemySide = enemySide;
        this.starter = starter;
        this.battleTime = startTime;
        this.finishBattleObservable = SingleSubject.create();
        this.battleEvents = new BattleEventsHandler();

        opponents.put(user, new ArrayList<>(enemySide));
        for (BattleParticipant userAlly : userSide) {
            opponents.put(userAlly, new ArrayList<>(enemySide));
        }
        for (BattleParticipant userEnemy : enemySide) {
            opponents.put(userEnemy, new ArrayList<>(userSide));
            opponents.get(userEnemy).add(user);
        }
    }

    public BaseBattle init() {
        initParticipants(opponents.keySet());
        return this;
    }

    @Override
    public void start() {
        starter.start(user, userSide, enemySide);
        isActive.set(true);
    }

    @Override
    public synchronized void update(float timeDelta) {
        if (!isActive.get()) return;

        if (checkFinish()) {
            isActive.set(false);
            finishBattleObservable.onSuccess(prepareSummaryData());
            return;
        }

        if (isActive.get()) {
            battleTime += timeDelta;

            battleEvents.update(battleTime);
            for (BattleParticipant battleParticipant : opponents.keySet()) {
                battleParticipant.update(battleTime);
            }
        }
    }

    @Override
    public void requestSkillApplication(BattleParticipant participant,
                                                     ActiveSkill skill, double castTime) {
        if (!isActive.get()) return;

        double activationTime = battleTime + castTime;

        List<BattleParticipant> targets = new ArrayList<>();
        List<BattleParticipant> participantOpponents = getOpponents(participant);

        List<Integer> targetsIndices = participant.getTargets();
        for (Integer i : targetsIndices){
            targets.add(participantOpponents.get(i));
        }
        battleEvents.add(activationTime, skill, participant, targets);
    }

    //region Private methods
    private BattleSummaryData prepareSummaryData() {
        BattleSummaryData summaryData = new BattleSummaryData();

        for (BattleParticipant bp : opponents.keySet()){
            summaryData.addStatisticsFrom(bp);
        }

        for (BattleParticipant bp : enemySide){
            if (!bp.isAlive()) {
                summaryData.addReward(bp.getOnDeathReward());
            }
        }
        return summaryData;
    }

    private void initParticipants(Iterable<BattleParticipant> participants) {
        for (BattleParticipant participant : participants) {
            participant.setBattleClientAPI(this);
            participant.changeTarget();
            for (Skill s : participant.getPassiveSkills()) {
                s.activate(participant, getOpponents(participant));
            }
        }
    }

    private boolean checkFinish() {
        return !user.isAlive() || isSideFinished(enemySide);
    }

    private boolean isSideFinished(List<BattleParticipant> side) {
        for (BattleParticipant participant : side) {
            if (participant.isAlive()) {
                return false;
            }
        }
        return true;
    }
    //endregion

    //region Getters and Setters
    @Override
    public List<BattleParticipant> getOpponents(BattleParticipant participant) {
        return opponents.get(participant);
    }

    @Override
    public BattleParticipant getUser() {
        return user;
    }

    @Override
    public List<BattleParticipant> getUserSide() {
        return userSide;
    }

    @Override
    public List<BattleParticipant> getEnemySide() {
        return enemySide;
    }

    @Override
    public SingleSubject<ReadableBattleSummaryData> getFinishBattleObservable() {
        return finishBattleObservable;
    }

    public double getBattleTime() {
        return battleTime;
    }
    //endregion

    @Override
    public void dispose() {}
}
