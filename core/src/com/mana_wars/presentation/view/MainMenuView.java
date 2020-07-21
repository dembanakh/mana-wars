package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.skills.Skill;

public interface MainMenuView extends BaseView {
    void openSkillCaseWindow(Skill skill);
    void setTimeSinceLastManaBonusClaimed(long time);
    void onManaBonusReady();
    void setUsername(String username);
    void setSkillCasesNumber(int number);
}
