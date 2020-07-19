package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.DataDeuce;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Integer> possibleOpponents = possibleOpponents(battle.getOpponents(participant));

        if (possibleOpponents.isEmpty())
            return currentTarget;

        currentTarget = possibleOpponents.get(Random.nextInt(possibleOpponents.size()));
        return currentTarget;
    }

    private List<Integer> possibleOpponents(List<BattleParticipant> opponents) {
        List<Integer> aliveOpponents = new ArrayList<>();
        for (int i = 0, n = opponents.size(); i < n; i++) {
            if (i != currentTarget && opponents.get(i).isAlive()){
                aliveOpponents.add(i);
            }
        }
        return aliveOpponents;
    }


    int getCurrent() {
        return currentTarget;
    }

    List<Integer> get(BattleClientAPI battle){
        List<Integer> targets = possibleOpponents(battle.getOpponents(participant));
        Collections.shuffle(targets);
        targets.add(0, currentTarget);
        return targets;
    }

}
