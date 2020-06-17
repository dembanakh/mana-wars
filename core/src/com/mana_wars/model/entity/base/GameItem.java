package com.mana_wars.model.entity.base;


public abstract class GameItem implements Comparable<GameItem> {

    private int iconID;
    private int level;
    private Rarity rarity;
    private String name;

    public GameItem(int iconID, int level, Rarity rarity, String name) {
        this.iconID = iconID;
        this.level = level;
        this.rarity = rarity;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void upgradeLevel() {
        level++;
        if (level > 10) level = 10;
    }

    public void downgradeLevel() {
        level--;
        if (level < 1) level = 1;
    }

    @Override
    public int compareTo(GameItem gameItem) {
        if (rarity != gameItem.rarity) return rarity.getId() - gameItem.rarity.getId();
        if (!name.equals(gameItem.name)) return -name.compareTo(gameItem.name);
        return level - gameItem.level;
    }

}
