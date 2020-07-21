package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.skills.Skill;

public interface ShopView extends BaseView {
    void openSkillCaseWindow(Skill skill);

    void setSkillCasesNumber(int number);
}
