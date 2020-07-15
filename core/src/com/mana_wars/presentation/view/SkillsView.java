package com.mana_wars.presentation.view;

import com.mana_wars.model.skills_operations.SkillTable;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;

public interface SkillsView extends BaseView {
    void setSkillsList(Iterable<ActiveSkill> activeSkills,
                       Iterable<PassiveSkill> passiveSkills,
                       Iterable<Skill> skills);
    void finishMerge(SkillTable table, int index, Skill skill);
    void finishSwap(SkillTable tableSource, SkillTable tableTarget,
                    int skillSourceIndex, int skillTargetIndex, Skill skillSource, Skill skillTarget);
    void finishMove(SkillTable tableTarget, int skillTargetIndex, Skill skillSource);

    void selectSkills(SkillTable table, Iterable<? extends Integer> mergeableIndices);
    void clearSelection();
}
