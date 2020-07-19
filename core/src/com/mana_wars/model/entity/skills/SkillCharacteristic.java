package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.base.Characteristic;

public class SkillCharacteristic {

    private final Characteristic characteristic;
    private final ValueChangeType changeType;

    /**
        target == 0 -> SELF
        target > 0 -> SELF + target amount of ALLIES
        target < 0 -> target amount of ENEMIES
     */
    private final int target;

    private final UpgradeFunction upgradeFunction;
    private final double levelMultiplier;

    private final int value;

    public SkillCharacteristic(int value, Characteristic characteristic, ValueChangeType changeType,
                               int target, UpgradeFunction upgradeFunction, int levelMultiplier) {
        this.characteristic = characteristic;
        this.value = value;
        this.changeType = changeType;
        this.target = target;
        this.upgradeFunction = upgradeFunction;
        this.levelMultiplier = levelMultiplier;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public ValueChangeType getChangeType() {
        return changeType;
    }

    public int getTarget() {
        return target;
    }

    public boolean isManaCost(){
        return characteristic==Characteristic.MANA && target == 0 && changeType == ValueChangeType.DECREASE;
    }

    public boolean isHealth(){
        return characteristic==Characteristic.HEALTH;
    }

    public int getValue(int skillLevel) {
        return upgradeFunction.apply(value, skillLevel, levelMultiplier);
    }

    public UpgradeFunction getUpgradeFunction() {
        return upgradeFunction;
    }

    public double getLevelMultiplier() {
        return levelMultiplier;
    }
}
