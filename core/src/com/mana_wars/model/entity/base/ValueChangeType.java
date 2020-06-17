package com.mana_wars.model.entity.base;

public enum ValueChangeType {
    INCREASE(1),
    DECREASE(-1);

    private final int value;

    ValueChangeType(int value) {
        this.value = value;
    }

    public int getConstant() {
        return value;
    }

}
