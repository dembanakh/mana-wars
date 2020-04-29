package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;

public class SkillCharacteristic {

    private int value;
    private Characteristic characteristic;
    private ValueChangeType changeType;
    private Target target;

    public enum Target{
        SELF,
        ENEMY
    }

    public int getValue() {
        return value;
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
}
