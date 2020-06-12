package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.base.Rarity;

public interface MainMenuView extends BaseView{

    void openSkillCaseWindow(int skillID, String skillName, Rarity skillRarity, String description);
    void setTimeSinceLastManaBonusClaimed(long time);
    void onManaBonusReady();

    void setUsername(String username);
}
