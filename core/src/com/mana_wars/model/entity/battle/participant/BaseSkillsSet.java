package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;

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
                    (battleSkill.skill == skill ? cooldown : 0));
        }
    }

    @Override
    public Iterator<Entry> iterator() {
        return new BaseSkillsIterator(skills.iterator());
    }

    private static class BaseSkillsIterator implements Iterator<Entry> {

        private final Iterator<BattleSkill> iterator;

        BaseSkillsIterator(Iterator<BattleSkill> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Entry next() {
            BattleSkill next = iterator.next();
            return new Entry(next.skill, next.getAvailabilityTime());
        }
    }
}
