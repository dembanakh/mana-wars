package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;

public enum BattleStartMode {
    DEFAULT {
        @Override
        public void start(BattleParticipant user,
                          Iterable<? extends BattleParticipant> userSide,
                          Iterable<? extends BattleParticipant> enemySide) {
            user.start();
            for (BattleParticipant participant : userSide) {
                participant.start();
            }
            for (BattleParticipant participant : enemySide) {
                participant.start();
            }
        }
    },
    ROUND {
        @Override
        public void start(BattleParticipant user,
                          Iterable<? extends BattleParticipant> userSide,
                          Iterable<? extends BattleParticipant> enemySide) {
            for (BattleParticipant participant : enemySide) {
                participant.start();
            }
        }
    };

    public abstract void start(BattleParticipant user,
                               Iterable<? extends BattleParticipant> userSide,
                               Iterable<? extends BattleParticipant> enemySide);
}
