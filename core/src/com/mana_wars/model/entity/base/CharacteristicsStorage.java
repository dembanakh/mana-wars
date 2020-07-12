package com.mana_wars.model.entity.base;

public interface CharacteristicsStorage {
    int getValue(Characteristic characteristic);
    void setValue(Characteristic characteristic, int value);
}
