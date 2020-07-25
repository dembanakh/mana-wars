package com.mana_wars.model.entity.dungeon;

import java.util.List;

public class Dungeon {

    private final int iconID;
    private final String name;
    private final int requiredLvl;
    private final List<DungeonRoundDescription> roundDescriptions;

    public Dungeon(int iconID, String name, int requiredLvl, List<DungeonRoundDescription> roundDescriptions) {
        this.iconID = iconID;
        this.name = name;
        this.requiredLvl = requiredLvl;
        this.roundDescriptions = roundDescriptions;
    }

    public int getIconID() {
        return iconID;
    }

    public String getName() {
        return name;
    }

    public int getRequiredLvl() {
        return requiredLvl;
    }

    public List<DungeonRoundDescription> getRoundDescriptions() {
        return roundDescriptions;
    }
}
