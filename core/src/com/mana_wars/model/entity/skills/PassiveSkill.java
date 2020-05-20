package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

import java.util.ArrayList;
import java.util.List;

public class PassiveSkill extends Skill {

    public PassiveSkill(int id, int level, Rarity rarity, int manaCost, String name, List<SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name, manaCost, skillCharacteristics);
    }

}
