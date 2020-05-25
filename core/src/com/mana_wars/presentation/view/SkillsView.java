package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

public interface SkillsView {
    void setSkillsList(List<Skill> activeSkills, List<Skill> passiveSkills, List<Skill> skills);
    void finishMerge(SkillTable table, int index, Skill skill);
    void finishSwap(SkillTable tableSource, SkillTable tableTarget,
                    int skillSourceIndex, int skillTargetIndex, Skill skillSource, Skill skillTarget);
    void finishMove(SkillTable tableTarget, int skillTargetIndex, Skill skillSource);
}
