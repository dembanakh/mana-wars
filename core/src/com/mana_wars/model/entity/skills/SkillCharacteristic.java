package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;

public class SkillCharacteristic {

    private Characteristic characteristic;
    private ValueChangeType changeType;
    private Target target;

    private int value;

    public enum Target {
        SELF,
        ENEMY
    }

    public SkillCharacteristic(int value, Characteristic characteristic, ValueChangeType changeType, Target target){
        this.characteristic = characteristic;
        this.value = value;
        this.changeType = changeType;
        this.target = target;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public ValueChangeType getChangeType() {
        return changeType;
    }

    public Target getTarget() {
        return target;
    }

    public int getValue(){
        return value;
    }
}
