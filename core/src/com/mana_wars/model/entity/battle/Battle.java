package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Battle {

    protected Map<BattleParticipant, List<BattleParticipant>> opponents = new HashMap<>();
    protected List<BattleParticipant> userSide;
    protected List<BattleParticipant> enemySide;
    protected User user;
    protected AtomicBoolean isActive = new AtomicBoolean(false);
    protected double battleTime;

    protected static class BattleEvent implements Comparable<BattleEvent> {
        double targetTime;
        ActiveSkill skill;
        BattleParticipant participant;

        public BattleEvent(double targetTime, ActiveSkill skill, BattleParticipant participant) {
            this.targetTime = targetTime;
            this.skill = skill;
            this.participant = participant;
        }

        @Override
        public int compareTo(BattleEvent battleEvent) {
            return (int) ((this.targetTime - battleEvent.targetTime)*10000); //TODO fix magic value
        }
    }

    protected PriorityQueue<BattleEvent> battleEvents = new PriorityQueue<>();

    public User getUser() {
        return user;
    }

    public List<BattleParticipant> getUserSide() {
        return userSide;
    }

    public List<BattleParticipant> getEnemySide() {
        return enemySide;
    }

    public Battle(User user, List<BattleParticipant> userSide, List<BattleParticipant> enemySide){
        this.user = user;
        this.userSide = userSide;
        this.enemySide = enemySide;
        for(BattleParticipant userAlly : userSide){
            opponents.put(userAlly, new ArrayList<>(enemySide));
        }
        for(BattleParticipant userEnemy : enemySide){
            opponents.put(userEnemy, new ArrayList<>(userSide));
        }
    }

    public void init(){
        for (BattleParticipant participant : userSide){
            for (Skill s : participant.getPassiveSkills()){
                s.activate(participant, getOpponents(participant).get(0));
            }
        }
        for (BattleParticipant participant : enemySide){
            for (Skill s : participant.getPassiveSkills()){
                s.activate(participant, getOpponents(participant).get(0));
            }
        }

        System.out.println("Battle inited");
    }

    public void start(){
        battleTime = 0;
        isActive.set(true);
    }

    public boolean update(double timeDelta){

        synchronized (this){
            if(isActive.get()){
                battleTime += timeDelta;
                while (!battleEvents.isEmpty() && battleEvents.peek().targetTime < battleTime){
                    activateParticipantSkill(battleEvents.poll());
                }
            }
        }
        return isFinished();
    }

    protected void activateParticipantSkill(BattleEvent be){
        //TODO refactor for multiple targets
        be.skill.activate(be.participant, getOpponents(be.participant).get(0));
    }

    private boolean isFinished(){
        boolean result = true;
        for (BattleParticipant participant : enemySide){
            if (participant.getCharacteristicValue(Characteristic.HEALTH)>0) {
                //System.out.println("Enemy Health " + participant.getCharacteristicValue(Characteristic.HEALTH));
                result = false;
                break;
            }
        }
        if(result){
            isActive.set(false);
            return true;
        }

        for (BattleParticipant participant : userSide){
            //System.out.println("user Health " + participant.getCharacteristicValue(Characteristic.HEALTH));
            if (participant.getCharacteristicValue(Characteristic.HEALTH)>0){
                return false;
            }
        }
        isActive.set(false);
        return true;
    }

    public List<BattleParticipant> getOpponents(BattleParticipant participant) {
        return opponents.get(participant);
    }

    public void applyActiveSkill(ActiveSkill skill, BattleParticipant participant) {
        //TODO think about synchronization
        synchronized (this){
            double currentTime = battleTime + skill.getCastTime();
            battleEvents.add(new BattleEvent(currentTime, skill, participant));
        }
    }
}
