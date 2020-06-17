package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;

public interface SkillsInfoView extends BaseView {
    void openSkillInfoWindow(int skillID, String skillName, Rarity skillRarity, String description);

    void setSkillsList(Iterable<Skill> skills);
}
