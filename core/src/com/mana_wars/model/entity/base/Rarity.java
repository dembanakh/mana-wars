package com.mana_wars.model.entity.base;

public enum Rarity {
    EMPTY(0),
    COMMON(1),
    RARE(2),
    ARCANE(3),
    HEROIC(4),
    EPIC(5),
    LEGENDARY(6),
    //MYTHIC(7)
    ;

    private final int id;

    Rarity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Rarity getRarityByID(int id) {
        Rarity res = null;
        for (Rarity r : values()) {
            if (r.id == id) {
                res = r;
                break;
            }
        }
        return res;
    }
}
