package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.ShopSkill;

public interface ShopView extends BaseView {
    void openSkillCaseWindow(Skill skill);
    void setSkillCasesNumber(int number);
    void setPurchasableSkills(Iterable<? extends ShopSkill> skills);
}
