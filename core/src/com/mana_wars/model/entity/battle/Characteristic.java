package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.base.ValueType;

public enum Characteristic {
    
    HEALTH(1, 0, ValueType.VALUE),
    MANA(2, 0,ValueType.VALUE),
    COOLDOWN(3, 10,ValueType.PERCENT),
    CAST_TIME(4, 10,ValueType.PERCENT);

    private final int id;
    private final int lowerBound;
    private final ValueType type;

    Characteristic(int id, int lowerBound, ValueType type) {
        this.id = id;
        this.lowerBound = lowerBound;
        this.type = type;
    }

    public int changeValue(int prev, ValueChangeType type, int diff){
        prev += type.getConstant()*diff;
        return validateValue(prev);
    }

    public ValueType getType() {
        return type;
    }

    private Integer validateValue(int value){
        return Math.max(value, lowerBound);
    }

    public static Characteristic getCharacteristicById(int id) {
        for (Characteristic characteristic : values()) {
            if (characteristic.id == id) return characteristic;
        }
        return null;
    }
}
