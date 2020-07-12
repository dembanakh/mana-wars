package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.data.BattleSummaryData;
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

    private final BattleSummaryData summaryData = new BattleSummaryData();
    private final SingleSubject<BattleSummaryData> finishBattleObservable;

    private final BattleEventsHandler battleEvents;
    private final BattleTime battleTime;
    private final BattleStarter starter;

    public BaseBattle(final BattleParticipant user,
               final List<BattleParticipant> userSide,
               final List<BattleParticipant> enemySide) {
        this(user, userSide, enemySide, new BaseBattleStarter(), new BaseBattleTime());
    }

    public BaseBattle(final BattleParticipant user,
               final List<BattleParticipant> userSide,
               final List<BattleParticipant> enemySide,
               final BattleStarter starter,
               final BattleTime battleTime) {
        this.user = user;
        this.userSide = userSide;
        this.enemySide = enemySide;
        this.starter = starter;
        this.battleTime = battleTime;
        this.finishBattleObservable = SingleSubject.create();
        this.battleEvents = new BattleEventsHandler(this);

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
            battleTime.update(timeDelta);
            battleEvents.update(battleTime.get());
            for (BattleParticipant battleParticipant : opponents.keySet()) {
                battleParticipant.update(battleTime.get());
            }
        }
    }

    @Override
    public synchronized void requestSkillApplication(BattleParticipant participant,
                                                     ActiveSkill skill, double castTime) {
        if (!isActive.get()) return;
        //TODO think about synchronization
        double activationTime = battleTime.get() + castTime;
        battleEvents.add(activationTime, skill, participant);
    }


    private BattleSummaryData prepareSummaryData() {
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
            for (Skill s : participant.getPassiveSkills()) {
                //TODO think about it
                s.activate(participant, getOpponents(participant).get(0));
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
    public SingleSubject<BattleSummaryData> getFinishBattleObservable() {
        return finishBattleObservable;
    }

    public BattleTime getBattleTime() {
        return battleTime;
    }

    @Override
    public void dispose() {}
}
