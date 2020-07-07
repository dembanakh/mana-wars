package com.mana_wars.ui.widgets.skills_list_2d;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.widgets.base.BuildableUI;

public interface BlockableSkillsList<T extends Skill> extends BuildableUI {
    void update(float delta);
    void blockSkills(int appliedSkillIndex);
    void setItems(Iterable<? extends T> items);
}
