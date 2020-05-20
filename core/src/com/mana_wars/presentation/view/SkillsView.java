package com.mana_wars.presentation.view;

import com.mana_wars.model.SkillsOperations;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

public interface SkillsView {
    void setSkillsList(List<Skill> activeSkills, List<Skill> passiveSkills, List<Skill> skills);
    void finishMerge(SkillsOperations.Table table, int index, Skill skill);
    void finishSwap(SkillsOperations.Table tableSource, SkillsOperations.Table tableTarget,
                    int skillSourceIndex, int skillTargetIndex, Skill skillSource, Skill skillTarget);
    void finishMove(SkillsOperations.Table tableTarget, int skillTargetIndex, Skill skillSource);
}
