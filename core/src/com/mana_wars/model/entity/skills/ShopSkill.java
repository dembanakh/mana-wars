package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

public class ShopSkill implements ReadableSkill {

    private Skill skill = Skill.getEmpty();
    private int price;

    public ShopSkill(Skill initialSkill) {
        //TODO TEMP
        setSkill(initialSkill, 100);
    }

    public Skill purchaseSkill() {
        Skill purchased = skill;
        skill = Skill.getEmpty();
        return purchased;
    }

    public int getPrice() {
        return price;
    }

    public void setSkill(Skill skill, int price) {
        this.skill = skill;
        this.price = price;
    }

    public boolean isPurchased() {
        return skill.getRarity() == Rarity.EMPTY;
    }

    @Override
    public int getIconID() {
        return skill.getIconID();
    }

    @Override
    public Rarity getRarity() {
        return skill.getRarity();
    }

    @Override
    public int getLevel() {
        return skill.getLevel();
    }

    @Override
    public int getManaCost() {
        return skill.getManaCost();
    }
}
