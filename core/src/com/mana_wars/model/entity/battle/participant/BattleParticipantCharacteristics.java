package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;

class BattleParticipantCharacteristics {

    private final int initialHealth;
    private final EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);

    private boolean consumeMana;

    BattleParticipantCharacteristics(int initialHealth){
        this.initialHealth = initialHealth;
        this.consumeMana = true;

        setCharacteristicValue(Characteristic.HEALTH, initialHealth);
        setCharacteristicValue(Characteristic.MANA, 0);
        setCharacteristicValue(Characteristic.CAST_TIME, 100);
        setCharacteristicValue(Characteristic.COOLDOWN, 100);
    }

    void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel){
        if (sc.isManaCost() && !consumeMana) return;

        Characteristic c = sc.getCharacteristic();
        int changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(), sc.getValue(skillLevel));

        if (sc.isHealth()) {
            changedValue = Math.min(changedValue, initialHealth);
        }
        setCharacteristicValue(c, changedValue);
    }

    void setCharacteristicValue(Characteristic type, int value) {
        characteristics.put(type, value);
    }

    int getCharacteristicValue(Characteristic type) {
        return characteristics.get(type);
    }

    int getInitialHealth() {
        return initialHealth;
    }

    void setConsumeMana(boolean consumeMana) {
        this.consumeMana = consumeMana;
    }
}
