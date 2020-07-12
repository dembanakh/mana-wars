package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.CharacteristicsStorage;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;

class BattleParticipantCharacteristics implements CharacteristicsStorage {

    private final int initialHealth;
    private final EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);

    BattleParticipantCharacteristics(int initialHealth){
        this.initialHealth = initialHealth;

        setValue(Characteristic.HEALTH, initialHealth);
        setValue(Characteristic.MANA, 0);
        setValue(Characteristic.CAST_TIME, 100);
        setValue(Characteristic.COOLDOWN, 100);
    }

    @Override
    public void setValue(Characteristic type, int value) {
        if (type == Characteristic.HEALTH) value = Math.min(initialHealth, value);
        characteristics.put(type, value);
    }

    @Override
    public int getValue(Characteristic type) {
        return characteristics.get(type);
    }

    int getInitialHealth() {
        return initialHealth;
    }
}
