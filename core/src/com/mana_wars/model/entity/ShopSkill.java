package com.mana_wars.model.entity;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ReadableSkill;
import com.mana_wars.model.entity.skills.Skill;

import static com.mana_wars.model.GameConstants.MAX_DAILY_SKILL_AMOUNT;

public class ShopSkill implements ReadableSkill {

    private final Skill skill;
    private int alreadyBought;
    private final int price;

    public ShopSkill(Skill skill, int alreadyBought, int price) {
        this.skill = skill;
        this.alreadyBought = alreadyBought;
        this.price = price;
    }

    public int purchaseSkill() {
        return ++alreadyBought;
    }

    public int getPrice() {
        return price;
    }

    public boolean canBePurchased() {
        return alreadyBought < MAX_DAILY_SKILL_AMOUNT;
    }

    @Override
    public int getIconID() {
        return canBePurchased() ? skill.getIconID() : Skill.getEmpty().getIconID();
    }

    @Override
    public Rarity getRarity() {
        return canBePurchased() ? skill.getRarity() : Skill.getEmpty().getRarity();
    }

    @Override
    public int getLevel() {
        return canBePurchased() ? skill.getLevel() : Skill.getEmpty().getLevel();
    }

    @Override
    public int getManaCost() {
        return canBePurchased() ? skill.getManaCost() : Skill.getEmpty().getManaCost();
    }
}
