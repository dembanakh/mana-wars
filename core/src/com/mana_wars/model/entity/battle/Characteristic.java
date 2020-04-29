package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.base.ValueType;

public enum Characteristic {
    HEALTH(ValueType.VALUE),
    MANA(ValueType.VALUE),
    COOLDOWN(ValueType.PERCENT),
    CAST_TIME(ValueType.PERCENT);

    private int value;
    private final ValueType type;

    Characteristic(ValueType type){
        this.type=type;
    }

    public int getValue() {
        return value;
    }

    public void initValue(int value){
        this.value=value;
    }

    public void changeValue(ValueChangeType type, int diff){
        this.value += type.getConstant()*diff;
    }

}
