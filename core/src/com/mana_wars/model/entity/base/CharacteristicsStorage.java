package com.mana_wars.model.entity.base;

import com.mana_wars.model.entity.base.Characteristic;

public interface CharacteristicsStorage {
    int getValue(Characteristic characteristic);
    void setValue(Characteristic characteristic, int value);
}
