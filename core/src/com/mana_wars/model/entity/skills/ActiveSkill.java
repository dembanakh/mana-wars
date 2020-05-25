package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

import java.util.ArrayList;
import java.util.List;

public class ActiveSkill extends Skill {

    protected double cooldown;
    protected double castTime;

    public ActiveSkill(int id, int level, Rarity rarity, int manaCost, double castTime, double cooldown,String name, List<SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name, manaCost, skillCharacteristics);
        this.castTime = castTime;
        this.cooldown = cooldown;
    }

    public double getCooldown() {
        return cooldown;
    }

    public double getCastTime() {
        return castTime;
    }
}
