package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

public interface BattleView {
    void setLabelText(String text);

    void setSkills(List<ActiveSkill> activeSkills, List<Skill> passiveSkills);

    void finishBattle();

    void startBattle();
}
