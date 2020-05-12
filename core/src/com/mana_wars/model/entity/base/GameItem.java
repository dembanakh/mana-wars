package com.mana_wars.model.entity.base;


public abstract class GameItem {

    protected int iconID;
    protected int level;
    protected Rarity rarity;

    public GameItem(int iconID, int level, Rarity rarity) {
        this.iconID = iconID;
        this.level = level;
        this.rarity = rarity;
    }

    public int getIconID() {
        return iconID;
    }

    public int getLevel() {
        return level;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
