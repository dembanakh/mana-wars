package com.mana_wars.model.entity.base;

public enum Rarity {
    COMMON(1),
    RARE(2),
    /*ARCANE,
    HEROIC,
    UNIQUE,
    CELESTIAL,
    DIVINE,
    EPIC,
    LEGENDARY,
    MYTHIC*/
    ;

    private final int id;

    Rarity(int id){
        this.id = id;
    }

    public static Rarity getRarityByID(int id){
        Rarity res = null;
        for(Rarity r : values()){
            if(r.id==id){
                res = r;
                break;
            }
        }
        return res;
    }
}
