package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.ImmutableBattleSkill;

public interface SkillsSet extends Iterable<ImmutableBattleSkill> {
    default void add(Iterable<? extends ActiveSkill> skills) {
        for (ActiveSkill skill : skills) add(skill);
    }

    void add(ActiveSkill skill);
    void onSkillApplied(ActiveSkill skill, double currentTime, double castTime, double cooldown);
}
