package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.participant.BattleClientAPI;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.PriorityQueue;
import java.util.Queue;

class BattleEventsHandler {

    private final Queue<BattleEvent> battleEvents = new PriorityQueue<>();
    private final BattleClientAPI battle;

    BattleEventsHandler(BattleClientAPI battle) {
        this.battle = battle;
    }

    void add(double activationTime, ActiveSkill skill, BattleParticipant participant) {
        battleEvents.add(new BattleEvent(activationTime, skill, participant));
    }

    void update(double currentTime) {
        while (!battleEvents.isEmpty() && battleEvents.peek().targetTime < currentTime) {
            BattleEvent be = battleEvents.poll();
            if (be.participant.isAlive())
                be.skill.activate(be.participant, getOpponent(be.participant));
        }
    }

    private BattleParticipant getOpponent(BattleParticipant participant) {
        return battle.getOpponents(participant).get(participant.getCurrentTarget());
    }

    private static class BattleEvent implements Comparable<BattleEvent> {
        private final double targetTime;
        private final ActiveSkill skill;
        private final com.mana_wars.model.entity.battle.participant.BattleParticipant participant;

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
