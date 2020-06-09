package com.mana_wars.model.entity.enemy;

import com.mana_wars.model.entity.battle.BattleParticipant;

import java.util.Collections;
import java.util.List;

public class FirstDungeonEnemyFactory extends BaseEnemyFactory {

    @Override
    public List<BattleParticipant> generateEnemies() {
        Mob mob = new Mob(500);
        return Collections.singletonList(mob);
    }

}
