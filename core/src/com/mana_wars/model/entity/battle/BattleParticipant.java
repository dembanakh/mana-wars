package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;


public class BattleParticipant {

    private EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);
    {
        for(Characteristic c : Characteristic.values()){
            characteristics.put(c,0);
        }
    }

    public BattleParticipant(int healthPoints, int manaPoints, int cooldown, int castTime) {
        this.characteristics.put(Characteristic.HEALTH, healthPoints);
        this.characteristics.put(Characteristic.MANA, manaPoints);
        this.characteristics.put(Characteristic.COOLDOWN, cooldown);
        this.characteristics.put(Characteristic.CAST_TIME, castTime);
    }

    public int getCharacteristicValue(Characteristic type){
        return characteristics.get(type);
    }

    public void applySkillCharacteristic(SkillCharacteristic sc){
        Characteristic c = sc.getCharacteristic();

        characteristics.put(c, c.changeValue(characteristics.get(c),sc.getChangeType(), sc.getValue()));
    }

}
