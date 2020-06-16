package com.mana_wars.model.entity.enemy;

public class Dungeon {

    private final int iconID;
    private final String name;
    private final int requiredLvl;

    public Dungeon(int iconID, String name, int requiredLvl) {
        this.iconID = iconID;
        this.name = name;
        this.requiredLvl = requiredLvl;
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
}
