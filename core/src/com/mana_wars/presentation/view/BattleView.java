package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

public interface BattleView {

    void setSkills(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills);
    void finishBattle();
    void startBattle();
}
