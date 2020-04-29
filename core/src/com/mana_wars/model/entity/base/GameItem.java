package com.mana_wars.model.entity.base;

public abstract class GameItem {

    protected int level;
    protected String iconPath;
    protected Rarity rarity;

    public GameItem(int level, String iconPath, Rarity rarity) {
        this.level = level;
        this.iconPath = iconPath;
        this.rarity = rarity;
    }

    public int getLevel() {
        return level;
    }

    public String getIconPath() {
        return iconPath;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
