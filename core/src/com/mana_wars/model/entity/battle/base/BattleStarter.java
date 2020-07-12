package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;

public interface BattleStarter {
    void start(BattleParticipant user, Iterable<BattleParticipant> userSide, Iterable<BattleParticipant> enemySide);
}
