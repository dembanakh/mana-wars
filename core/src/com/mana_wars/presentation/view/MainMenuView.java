package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.base.Rarity;

import java.util.List;

public interface MainMenuView {

    void openSkillCaseWindow(int skillID, String skillName, Rarity skillRarity, String description);
    void setTimeSinceLastManaBonusClaimed(long time);
    void onManaBonusReady();

}
