package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;

class BaseBattleStarter implements BattleStarter {
    @Override
    public void start(BattleParticipant user, Iterable<BattleParticipant> userSide, Iterable<BattleParticipant> enemySide) {
        user.start();
        for (BattleParticipant participant : userSide) {
            participant.start();
        }
        for (BattleParticipant participant : enemySide) {
            participant.start();
        }
    }
}
