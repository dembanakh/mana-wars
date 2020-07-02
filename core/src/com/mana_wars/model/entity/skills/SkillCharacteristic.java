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

    public SkillCharacteristic(int value, Characteristic characteristic, ValueChangeType changeType,
                               Target target, UpgradeFunction upgradeFunction, int levelMultiplier) {
        this.characteristic = characteristic;
        this.value = value;
        this.changeType = changeType;
        this.target = target;
        this.upgradeFunction = upgradeFunction;
        this.levelMultiplier = levelMultiplier;
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

    public boolean isManaCost(){
        return characteristic==Characteristic.MANA && target == Target.SELF && changeType == ValueChangeType.DECREASE;
    }

    public int getValue(int skillLevel) {
        return upgradeFunction.apply(value, skillLevel, levelMultiplier);
    }

}
