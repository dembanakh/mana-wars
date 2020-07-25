package com.mana_wars.model.entity.dungeon;

import com.mana_wars.model.entity.base.EnemyFactory;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonEnemyFactory implements EnemyFactory {

    private static Random Random = new Random();

    private final Dungeon dungeon;
    private List<MobBlueprint> mobs;
    private List<MobBlueprint> bosses;

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
    public List<BattleParticipant> generateEnemies(int currentRound) {
        DungeonRoundDescription description = dungeon.getRoundDescriptions().get(currentRound - 1);
        int mobsCount = description.getMinOpponents() + Random.nextInt(description.getMaxOpponents() - description.getMinOpponents() + 1);
        List<BattleParticipant> result = new ArrayList<>();
        for (int i = 0; i < mobsCount; i++) result.add(generateMob());
        return result;
    }

    private Mob generateMob() {
        return new Mob(mobs.get(Random.nextInt(mobs.size())));
    }
}
