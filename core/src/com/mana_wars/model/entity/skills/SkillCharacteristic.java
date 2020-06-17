package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;

public class SkillCharacteristic {

    private final Characteristic characteristic;
    private final ValueChangeType changeType;
    private final Target target;

    private final UpgradeFunction upgradeFunction;
    private final double levelMultiplier;

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
        // TODO: TEMP
        this.upgradeFunction = UpgradeFunction.LINEAR;
        this.levelMultiplier = (characteristic == Characteristic.MANA ? 0 : 1);
    }

    String getDescription(int skillLevel) {
        String result = String.valueOf(target);
        result += " ";
        result += String.valueOf(characteristic);
        result += (changeType == ValueChangeType.DECREASE) ? " -" : " +";
        result += String.valueOf(getValue(skillLevel));
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

    public int getValue(int skillLevel) {
        return upgradeFunction.apply(value, skillLevel, levelMultiplier);
    }

}
