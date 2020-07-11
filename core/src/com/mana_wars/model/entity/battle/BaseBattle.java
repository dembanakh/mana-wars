package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.battle.participant.BattleParticipantBattleAPI;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.subjects.SingleSubject;

public class BaseBattle implements Battle, BattleParticipantBattleAPI {

    private final Map<BattleParticipant, List<BattleParticipant>> opponents = new HashMap<>();
    private final List<BattleParticipant> userSide;
    private final List<BattleParticipant> enemySide;
    private final BattleParticipant user;

    private final BattleSummaryData summaryData = new BattleSummaryData();
    private final SingleSubject<BattleSummaryData> finishBattleObservable;

    private final AtomicBoolean isActive = new AtomicBoolean(false);

    private final PriorityQueue<BattleEvent> battleEvents = new PriorityQueue<>();
    private double battleTime;

    BaseBattle(final BattleParticipant user,
               final List<BattleParticipant> userSide,
               final List<BattleParticipant> enemySide) {
        this.user = user;
        this.userSide = userSide;
        this.enemySide = enemySide;
        this.finishBattleObservable = SingleSubject.create();

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
        battleTime = 0;
        startParticipants(opponents.keySet());
        isActive.set(true);
    }

    public void start(double startTime) {
        battleTime = startTime;
        startParticipants(enemySide);
        isActive.set(true);
    }

    @Override
    public synchronized void update(float timeDelta) {
        if (checkFinish()) {
            isActive.set(false);
            finishBattleObservable.onSuccess(prepareSummaryData());
        }

        if (isActive.get()) {
            battleTime += timeDelta;

            while (!battleEvents.isEmpty() && battleEvents.peek().targetTime < battleTime) {
                BattleEvent be = battleEvents.poll();

                if (be.participant.isAlive())
                    activateParticipantSkill(be);
            }

            for (BattleParticipant battleParticipant : opponents.keySet()) {
                battleParticipant.update(battleTime);
            }
        }
    }

    private BattleSummaryData prepareSummaryData(){
        for (BattleParticipant bp : enemySide){
            if(!bp.isAlive()){
                summaryData.addReward(bp.getOnDeathReward());
            }
        }
        return summaryData;
    }

    @Override
    public synchronized void requestSkillApplication(BattleParticipant participant,
                                                     ActiveSkill skill, double castTime) {
        //TODO think about synchronization
        double activationTime = battleTime + castTime;
        battleEvents.add(new BattleEvent(activationTime, skill, participant));
    }

    private void activateParticipantSkill(BattleEvent be) {
        be.skill.activate(be.participant, getOpponents(be.participant).get(be.participant.getCurrentTarget()));
    }

    private void initParticipants(Iterable<BattleParticipant> participants) {
        for (BattleParticipant participant : participants) {
            participant.setBattleParticipantBattleAPI(this);
            for (Skill s : participant.getPassiveSkills()) {
                //TODO think about it
                s.activate(participant, getOpponents(participant).get(0));
            }
        }
    }

    private void startParticipants(Iterable<BattleParticipant> participants) {
        for (BattleParticipant participant : participants) {
            participant.start();
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

    private static class BattleEvent implements Comparable<BattleEvent> {
        private final double targetTime;
        private final ActiveSkill skill;
        private final BattleParticipant participant;

        BattleEvent(double targetTime, ActiveSkill skill, BattleParticipant participant) {
            this.targetTime = targetTime;
            this.skill = skill;
            this.participant = participant;
        }

        @Override
        public int compareTo(BattleEvent battleEvent) {
            return Double.compare(this.targetTime, battleEvent.targetTime);
        }
    }

    public double getBattleTime() {
        return battleTime;
    }


    @Override
    public void dispose() {}
}
