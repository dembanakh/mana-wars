package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.ActiveSkill;

public interface SkillsSet extends Iterable<SkillsSet.Entry> {
    default void add(Iterable<? extends ActiveSkill> skills) {
        for (ActiveSkill skill : skills) add(skill);
    }

    void add(ActiveSkill skill);
    void onSkillApplied(ActiveSkill skill, double currentTime, double castTime, double cooldown);

    class Entry {
        public final ActiveSkill skill;
        private final double availabilityTime;

        Entry(ActiveSkill skill, double availabilityTime) {
            this.skill = skill;
            this.availabilityTime = availabilityTime;
        }

        public boolean isAvailableAt(double time) {
            return Double.compare(time, this.availabilityTime) >= 0;
        }
    }
}
