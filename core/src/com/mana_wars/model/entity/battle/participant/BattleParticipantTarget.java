package com.mana_wars.model.entity.battle.participant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BattleParticipantTarget {

    private static Random Random = new Random();
    private int currentTarget = -1;

    private final BattleParticipant participant;

    BattleParticipantTarget(BattleParticipant participant) {
        this.participant = participant;
    }

    int change(BattleClientAPI battle) {
        List<Integer> aliveOpponents = aliveOpponents(battle.getOpponents(participant));

        if (aliveOpponents.isEmpty())
            return currentTarget;

        currentTarget = aliveOpponents.get(Random.nextInt(aliveOpponents.size()));
        return currentTarget;
    }

    private List<Integer> aliveOpponents(List<BattleParticipant> opponents) {
        List<Integer> aliveOpponents = new ArrayList<>();
        for (int i = 0, n = opponents.size(); i < n; i++) {
            if (i != currentTarget && opponents.get(i).isAlive()){
                aliveOpponents.add(i);
            }
        }
        return aliveOpponents;
    }

    int get() {
        return currentTarget;
    }

}
