package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;

public class BattleParticipantCharacteristics {

    private final int initialHealth;
    private final EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);

    BattleParticipantCharacteristics(int initialHealth){
        this.initialHealth = initialHealth;

        setCharacteristicValue(Characteristic.HEALTH, initialHealth);
        setCharacteristicValue(Characteristic.MANA, 0);
        setCharacteristicValue(Characteristic.CAST_TIME, 100);
        setCharacteristicValue(Characteristic.COOLDOWN, 100);
        //game private data
        setCharacteristicValue(Characteristic._MANA_COST, 100);
    }

    public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel){

        Characteristic c = sc.getCharacteristic();

        int changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(), sc.getValue(skillLevel));

        //TODO very strongly think about it!
        if(sc.isManaCost()){
            changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(),
                    sc.getValue(skillLevel) * getCharacteristicValue(Characteristic._MANA_COST) / 100);
        }

        if (sc.isHealth()) {
            changedValue = Math.min(changedValue, initialHealth);
        }
        setCharacteristicValue(c, changedValue);

    }

    protected void setCharacteristicValue(Characteristic type, int value) {
        characteristics.put(type, value);
    }

    public int getCharacteristicValue(Characteristic type) {
        return characteristics.get(type);
    }

    public int getInitialHealth() {
        return initialHealth;
    }
}
