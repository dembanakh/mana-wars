package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.base.ValueType;

public enum Characteristic {
    HEALTH(0, ValueType.VALUE),
    MANA(0,ValueType.VALUE),
    COOLDOWN(10,ValueType.PERCENT),
    CAST_TIME(10,ValueType.PERCENT);

    private int value;
    private final int lowerBorder;
    private final ValueType type;


    Characteristic(int lowerBorder, ValueType type) {
        this.lowerBorder = lowerBorder;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void initValue(int value){
        this.value=value;
    }

    public void changeValue(ValueChangeType type, int diff){
        this.value += type.getConstant()*diff;
        validateValue();
    }

    public ValueType getType() {
        return type;
    }

    private void validateValue(){
        value = Math.max(value, lowerBorder);
    }
}
