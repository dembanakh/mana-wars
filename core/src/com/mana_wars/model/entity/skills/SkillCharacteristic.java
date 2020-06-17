package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;

public class SkillCharacteristic {

    private final Characteristic characteristic;
    private final ValueChangeType changeType;
    private final Target target;

    private final int value;

    public enum Target {
        SELF,
        ENEMY
    }

    public SkillCharacteristic(int value, Characteristic characteristic, ValueChangeType changeType, Target target) {
        this.characteristic = characteristic;
        this.value = value;
        this.changeType = changeType;
        this.target = target;
    }

    String getDescription() {
        String result = String.valueOf(target);
        result += " ";
        result += String.valueOf(characteristic);
        result += (changeType == ValueChangeType.DECREASE) ? " -" : " +";
        result += String.valueOf(value);
        return result;
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

    public int getValue() {
        return value;
    }

}
