package com.mana_wars.model.entity.battle;

import java.util.List;

public interface BattleRoundsObserver {
    void setCurrentRound(int round);
    void setEnemies(BattleParticipant user, List<BattleParticipant> enemySide);
}
