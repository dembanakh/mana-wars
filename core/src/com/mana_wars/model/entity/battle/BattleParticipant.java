package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;
import java.util.List;


public class BattleParticipant {

    protected List<Skill> passive_skills;

    private EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);
    {
        for(Characteristic c : Characteristic.values()) {
            characteristics.put(c,0);
        }
    }

    public BattleParticipant(int healthPoints, int manaPoints) {
        this.characteristics.put(Characteristic.HEALTH, healthPoints);
        this.characteristics.put(Characteristic.MANA, manaPoints);
        this.characteristics.put(Characteristic.COOLDOWN, 100);
        this.characteristics.put(Characteristic.CAST_TIME,100);
    }

    public int getCharacteristicValue(Characteristic type){
        return characteristics.get(type);
    }

    public void applySkillCharacteristic(SkillCharacteristic sc){
        Characteristic c = sc.getCharacteristic();
        characteristics.put(c, c.changeValue(characteristics.get(c),sc.getChangeType(), sc.getValue()));
    }

    public void setPassive_skills(List<Skill> passive_skills) {
        this.passive_skills = passive_skills;
    }

    public List<Skill> getPassive_skills() {
        return passive_skills;
    }
}
