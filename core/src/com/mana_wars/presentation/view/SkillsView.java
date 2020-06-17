package com.mana_wars.presentation.view;

import com.mana_wars.model.skills_operations.SkillTable;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

public interface SkillsView extends BaseView {
    void setSkillsList(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills, List<Skill> skills);
    void finishMerge(SkillTable table, int index, Skill skill);
    void finishSwap(SkillTable tableSource, SkillTable tableTarget,
                    int skillSourceIndex, int skillTargetIndex, Skill skillSource, Skill skillTarget);
    void finishMove(SkillTable tableTarget, int skillTargetIndex, Skill skillSource);
}
