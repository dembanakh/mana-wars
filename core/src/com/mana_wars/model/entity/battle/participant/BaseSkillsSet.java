package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.ImmutableBattleSkill;

import java.util.ArrayList;
import java.util.List;

class BaseSkillsSet implements SkillsSet {

    private final List<BattleSkill> skills = new ArrayList<>();

    BaseSkillsSet(Iterable<? extends ActiveSkill> activeSkills) {
        for (ActiveSkill skill : activeSkills) {
            skills.add(new BattleSkill(skill));
        }
    }

    @Override
    public void onSkillApplied(ActiveSkill skill, double currentTime, double castTime, double cooldown) {
        for (BattleSkill battleSkill : skills) {
            battleSkill.updateAvailabilityTime(currentTime + castTime +
                    (battleSkill.getSkill() == skill ? cooldown : 0));
        }
    }

    public Iterable<? extends ImmutableBattleSkill> elements() {
        return skills;
    }

    private static class BattleSkill implements ImmutableBattleSkill {
        private double availabilityTime;
        private final ActiveSkill skill;

        BattleSkill(ActiveSkill skill) {
            this.skill = skill;
            this.availabilityTime = 0;
        }

        void updateAvailabilityTime(double possibleTime) {
            availabilityTime = Math.max(possibleTime, availabilityTime);
        }

        @Override
        public boolean isAvailableAt(double currentTime) {
            return Double.compare(currentTime, availabilityTime) >= 0;
        }

        @Override
        public ActiveSkill getSkill() {
            return skill;
        }
    }
}
