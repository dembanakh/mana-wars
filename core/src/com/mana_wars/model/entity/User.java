package com.mana_wars.model.entity;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.Skill;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import static com.mana_wars.model.GameConstants.USER_ACTIVE_SKILL_COUNT;
import static com.mana_wars.model.GameConstants.USER_PASSIVE_SKILL_COUNT;

public class User extends BattleParticipant {

    public User() {
        super(1000, 1000);
    }
}
