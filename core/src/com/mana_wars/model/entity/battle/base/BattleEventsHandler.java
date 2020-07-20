package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class BattleEventsHandler {

    private final Queue<BattleEvent> battleEvents = new PriorityQueue<>();

    synchronized void add(double activationTime, ActiveSkill skill, BattleParticipant participant, List<BattleParticipant> target) {
        battleEvents.add(new BattleEvent(activationTime, skill, participant, target));
    }

    void update(double currentTime) {
        while (!battleEvents.isEmpty() && battleEvents.peek().targetTime <= currentTime) {
            BattleEvent be = battleEvents.poll();
            if (be.source.isAlive()){

                //TODO refactor
                List<Integer> targetsHealthDelta = new ArrayList<>();
                int sourceHealthDelta = - be.source.getCharacteristicValue(Characteristic.HEALTH);
                for (BattleParticipant tbp : be.target){
                    targetsHealthDelta.add(- tbp.getCharacteristicValue(Characteristic.HEALTH));
                }

                be.skill.activate(be.source, be.target);

                sourceHealthDelta += be.source.getCharacteristicValue(Characteristic.HEALTH);
                for (int i=0; i < be.target.size(); i++){
                    targetsHealthDelta.set(i, targetsHealthDelta.get(i) + be.target.get(i).getCharacteristicValue(Characteristic.HEALTH));
                }

                be.source.getBattleStatisticsData().updateValuesAsSourceSelf(sourceHealthDelta);
                for (int i=0; i < be.target.size(); i++){
                    be.source.getBattleStatisticsData().updateValuesAsSourceTarget(targetsHealthDelta.get(i));
                    be.target.get(i).getBattleStatisticsData().updateValuesAsTarget(targetsHealthDelta.get(i));
                }
            }

        }
    }

    private static class BattleEvent implements Comparable<BattleEvent> {
        private final double targetTime;
        private final ActiveSkill skill;
        private final BattleParticipant source;
        private final List<BattleParticipant> target;

        BattleEvent(double targetTime, ActiveSkill skill, BattleParticipant source, List<BattleParticipant> target) {
            this.targetTime = targetTime;
            this.skill = skill;
            this.source = source;
            this.target = target;
        }

        @Override
        public int compareTo(BattleEvent battleEvent) {
            return Double.compare(this.targetTime, battleEvent.targetTime);
        }
    }
}
