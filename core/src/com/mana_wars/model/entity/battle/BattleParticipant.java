package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;


public class BattleParticipant {

    private EnumMap<Characteristic, Characteristic> characteristics = new EnumMap<>(Characteristic.class);
    {
        for(Characteristic c : Characteristic.values()){
            characteristics.put(c,c);
        }
    }

    public BattleParticipant(int healthPoints, int manaPoints, int cooldown, int castTime) {
        this.characteristics.get(Characteristic.HEALTH).initValue(healthPoints);
        this.characteristics.get(Characteristic.MANA).initValue(manaPoints);
        this.characteristics.get(Characteristic.COOLDOWN).initValue(cooldown);
        this.characteristics.get(Characteristic.CAST_TIME).initValue(castTime);
    }

    public int getCharacteristicValue(Characteristic type){
        return characteristics.get(type).getValue();
    }

    public void applySkillCharacteristic(SkillCharacteristic sc){
        characteristics.get(sc.getCharacteristic())
                .changeValue(sc.getChangeType(), sc.getValue());
    }

}
