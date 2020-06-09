package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

import java.util.ArrayList;
import java.util.List;

public class PassiveSkill extends Skill {

    public PassiveSkill(int id, int level, Rarity rarity, String name, List<SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name, skillCharacteristics);
    }

    private static PassiveSkill Empty = new PassiveSkill(50, 0, Rarity.EMPTY,
             "EMPTY", new ArrayList<>());

    public static PassiveSkill getEmpty() {
        return Empty;
    }
}
