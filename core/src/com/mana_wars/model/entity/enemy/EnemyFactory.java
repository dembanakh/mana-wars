package com.mana_wars.model.entity.enemy;

import com.mana_wars.model.entity.battle.BattleParticipant;

import java.util.List;

public interface EnemyFactory {

    List<BattleParticipant> generateEnemies();
}
