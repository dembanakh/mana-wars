package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

import java.util.ArrayList;
import java.util.List;

public class ActiveSkill extends Skill {

    private final double cooldown;
    private final double castTime;

    public ActiveSkill(int id, int level, Rarity rarity, double castTime, double cooldown, String name, List<SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name, skillCharacteristics);
        this.castTime = castTime;
        this.cooldown = cooldown;
    }

    public double getCooldown() {
        return cooldown;
    }

    public double getCastTime() {
        return castTime;
    }

    private static ActiveSkill Empty = new ActiveSkill(50, 0, Rarity.EMPTY, 0, 0, "EMPTY",
            new ArrayList<>());

    public static ActiveSkill getEmpty() {
        return Empty;
    }

}
