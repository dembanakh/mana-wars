package com.mana_wars.model.entity.enemy;

public class Dungeon {

    private final int iconID;
    private final String name;
    private final int requiredLvl;
    private final int rounds;

    public Dungeon(int iconID, String name, int requiredLvl, int rounds) {
        this.iconID = iconID;
        this.name = name;
        this.requiredLvl = requiredLvl;
        this.rounds = rounds;
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

    public int getRounds() {
        return rounds;
    }
}
