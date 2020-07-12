package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.ImmutableBattleSkill;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class BaseSkillsSet implements SkillsSet {

    private final List<BattleSkill> skills = new ArrayList<>();

    @Override
    public void add(ActiveSkill skill) {
        skills.add(new BattleSkill(skill));
    }

    @Override
    public void onSkillApplied(ActiveSkill skill, double currentTime, double castTime, double cooldown) {
        for (BattleSkill battleSkill : skills) {
            battleSkill.updateAvailabilityTime(currentTime + castTime +
                    (battleSkill.getSkill() == skill ? cooldown : 0));
        }
    }

    @Override
    public Iterator<ImmutableBattleSkill> iterator() {
        return new Iterator<ImmutableBattleSkill>() {
            private final Iterator<BattleSkill> iterator = skills.iterator();
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
            @Override
            public ImmutableBattleSkill next() {
                return iterator.next();
            }
        };
    }
}
