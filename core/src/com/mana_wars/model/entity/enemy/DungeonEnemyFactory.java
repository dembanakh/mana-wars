package com.mana_wars.model.entity.enemy;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DungeonEnemyFactory implements EnemyFactory {

    private static Random Random = new Random();

    private final Dungeon dungeon;
    private List<MobBlueprint> mobs;

    public DungeonEnemyFactory(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public void setMobs(List<MobBlueprint> mobs) {
        this.mobs = mobs;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    @Override
    public List<BattleParticipant> generateEnemies() {
        return Arrays.asList(new Mob(mobs.get(Random.nextInt(mobs.size()))),
                new Mob(mobs.get(Random.nextInt(mobs.size()))));
    }
}
