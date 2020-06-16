package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class BaseBattle implements BattleConfig, Battle {

    private final Map<BattleParticipant, List<BattleParticipant>> opponents = new HashMap<>();
    private final List<BattleParticipant> userSide;
    private final List<BattleParticipant> enemySide;
    private final BattleParticipant user;
    private final AtomicBoolean isActive = new AtomicBoolean(false);

    double battleTime;
    private PriorityQueue<BattleEvent> battleEvents = new PriorityQueue<>();

    BaseBattle(final BattleParticipant user,
               final List<BattleParticipant> userSide,
               final List<BattleParticipant> enemySide){
        this.user = user;
        this.userSide = userSide;
        this.enemySide = enemySide;

        opponents.put(user, new ArrayList<>(enemySide));
        for(BattleParticipant userAlly : userSide){
            opponents.put(userAlly, new ArrayList<>(enemySide));
        }
        for(BattleParticipant userEnemy : enemySide){
            opponents.put(userEnemy, new ArrayList<>(userSide));
            opponents.get(userEnemy).add(user);
        }
    }

    @Override
    public void init() {
        user.setBattle(this);
        for (Skill s : user.getPassiveSkills()) {
            s.activate(user, getOpponents(user).get(0));
        }
        initSide(userSide);
        initSide(enemySide);
        System.out.println("Battle inited");
    }

    @Override
    public void start(){
        battleTime = 0;
        user.start();
        startSide(userSide);
        startSide(enemySide);
        isActive.set(true);
    }

    @Override
    public synchronized void update(float timeDelta){
        if (isActive.get()) {
            battleTime += timeDelta;

            while (!battleEvents.isEmpty() && battleEvents.peek().targetTime < battleTime){
                BattleEvent be = battleEvents.poll();
                if (be.participant.isAlive())
                    activateParticipantSkill(be);
            }

            user.update(battleTime);
            for (BattleParticipant battleParticipant : getUserSide()) {
                battleParticipant.update(battleTime);
            }
            for (BattleParticipant battleParticipant : getEnemySide()) {
                battleParticipant.update(battleTime);
            }
        }
    }

    @Override
    public boolean checkFinish() {
        return !user.isAlive() || isSideFinished(enemySide);
    }

    @Override
    public void finish() {
        isActive.set(false);
    }

    @Override
    public synchronized void requestSkillApplication(BattleParticipant participant, ActiveSkill skill, double castTime) {
        //TODO think about synchronization
        double activationTime = battleTime + castTime;
        battleEvents.add(new BattleEvent(activationTime, skill, participant));
    }

    // TODO: remove after Mob::update has been implemented
    synchronized void addBattleEvent(BattleEvent event) {
        battleEvents.add(event);
    }

    void activateParticipantSkill(BattleEvent be) {
        //TODO refactor for multiple targets
        be.skill.activate(be.participant, getOpponents(be.participant).get(0));
    }


    private void initSide(Iterable<BattleParticipant> side) {
        for (BattleParticipant participant : side) {
            participant.setBattle(this);
            for (Skill s : participant.getPassiveSkills()) {
                s.activate(participant, getOpponents(participant).get(0));
            }
        }
    }

    private void startSide(List<BattleParticipant> side) {
        for (BattleParticipant participant : side) {
            participant.start();
        }
    }

    private boolean isSideFinished(List<BattleParticipant> side) {
        for (BattleParticipant participant : side) {
            if (participant.isAlive()){
                return false;
            }
        }
        return true;
    }

    private List<BattleParticipant> getOpponents(BattleParticipant participant) {
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

    // TODO: make private after Mob::update has been implemented
    protected static class BattleEvent implements Comparable<BattleEvent> {
        private final double targetTime;
        final ActiveSkill skill;
        final BattleParticipant participant;

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

}
