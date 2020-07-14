package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.ImmutableBattleSkill;

public interface SkillsSet {
    void onSkillApplied(ActiveSkill skill, double currentTime, double castTime, double cooldown);
    Iterable<? extends ImmutableBattleSkill> elements();
}
