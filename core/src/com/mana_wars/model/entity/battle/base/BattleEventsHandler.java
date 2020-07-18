package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.PriorityQueue;
import java.util.Queue;

class BattleEventsHandler {

    private final Queue<BattleEvent> battleEvents = new PriorityQueue<>();

    synchronized void add(double activationTime, ActiveSkill skill, BattleParticipant participant, BattleParticipant target) {
        battleEvents.add(new BattleEvent(activationTime, skill, participant, target));
    }

    void update(double currentTime) {
        while (!battleEvents.isEmpty() && battleEvents.peek().targetTime <= currentTime) {
            BattleEvent be = battleEvents.poll();
            if (be.source.isAlive()){

                //TODO refactore
                int self_health_before = be.source.getCharacteristicValue(Characteristic.HEALTH);
                int target_health_before = be.target.getCharacteristicValue(Characteristic.HEALTH);

                be.skill.activate(be.source, be.target);

                int self_health_after = be.source.getCharacteristicValue(Characteristic.HEALTH);
                int target_health_after = be.target.getCharacteristicValue(Characteristic.HEALTH);

                int source_delta = self_health_after - self_health_before;
                int target_delta = target_health_after - target_health_before;

                be.source.getBattleStatisticsData().updateValuesAsSource(source_delta, target_delta);
                be.target.getBattleStatisticsData().updateValuesAsTarget(target_delta);
            }

        }
    }

    private static class BattleEvent implements Comparable<BattleEvent> {
        private final double targetTime;
        private final ActiveSkill skill;
        private final BattleParticipant source;
        private final BattleParticipant target;

        BattleEvent(double targetTime, ActiveSkill skill, BattleParticipant source, BattleParticipant target) {
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
