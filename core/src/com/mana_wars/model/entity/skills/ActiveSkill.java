package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

import java.util.ArrayList;
import java.util.List;

public class ActiveSkill extends Skill {

    protected int cooldown;

    public ActiveSkill(int id, int level, Rarity rarity, int manaCost, int cooldown,String name, List<SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name, manaCost, skillCharacteristics);
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

}
