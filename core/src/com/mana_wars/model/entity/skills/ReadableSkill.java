package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

public interface ReadableSkill {
    int getIconID();
    Rarity getRarity();
    int getLevel();
    int getManaCost();
}
