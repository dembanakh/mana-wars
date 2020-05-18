package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.base.ValueType;

public enum Characteristic {
    HEALTH(0, ValueType.VALUE),
    MANA(0,ValueType.VALUE),
    COOLDOWN(10,ValueType.PERCENT),
    CAST_TIME(10,ValueType.PERCENT);


    private final int lowerBound;
    private final ValueType type;

    Characteristic(int lowerBound, ValueType type) {
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
}
