package com.mana_wars.model.entity.dungeon;

public class DungeonRoundDescription {

    private final boolean isWithBoss;
    private final int minOpponents;
    private final int maxOpponents;

    public DungeonRoundDescription(boolean isWithBoss, int minOpponents, int maxOpponents) {
        this.isWithBoss = isWithBoss;
        this.minOpponents = minOpponents;
        this.maxOpponents = maxOpponents;
    }

    public boolean isWithBoss() {
        return isWithBoss;
    }

    public int getMinOpponents() {
        return minOpponents;
    }

    public int getMaxOpponents() {
        return maxOpponents;
    }
}
