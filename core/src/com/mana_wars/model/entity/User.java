package com.mana_wars.model.entity;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.Skill;

import static com.mana_wars.model.GameConstants.USER_ACTIVE_SKILL_COUNT;
import static com.mana_wars.model.GameConstants.USER_PASSIVE_SKILL_COUNT;

public class User extends BattleParticipant {

    private Skill[] activeSkills = new Skill[USER_ACTIVE_SKILL_COUNT];
    private Skill[] passiveSkills = new Skill[USER_PASSIVE_SKILL_COUNT];

    public User(int healthPoints, int manaPoints, int cooldown, int castTime) {
        super(healthPoints, manaPoints, cooldown, castTime);
    }
}
