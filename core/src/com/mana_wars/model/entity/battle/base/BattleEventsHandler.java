package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.PriorityQueue;
import java.util.Queue;

class BattleEventsHandler {

    private final Queue<BattleEvent> battleEvents = new PriorityQueue<>();

    void add(double activationTime, ActiveSkill skill, BattleParticipant participant, BattleParticipant target) {
        battleEvents.add(new BattleEvent(activationTime, skill, participant, target));
    }

    void update(double currentTime) {
        while (!battleEvents.isEmpty() && battleEvents.peek().targetTime < currentTime) {
            BattleEvent be = battleEvents.poll();
            if (be.source.isAlive())
                be.skill.activate(be.source, be.target);
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
