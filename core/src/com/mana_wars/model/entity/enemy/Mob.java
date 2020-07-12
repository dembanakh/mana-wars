package com.mana_wars.model.entity.enemy;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.battle.data.BattleRewardData;
import com.mana_wars.model.entity.battle.participant.SkillsSet;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.ImmutableBattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

public class Mob extends BattleParticipant {

    public Mob(String name, int initialHealth, Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills, int manaReward, int experienceReward, int caseProbabilityReward) {
        super(name, initialHealth, activeSkills, passiveSkills, manaReward, experienceReward, caseProbabilityReward);
    }

    private Mob(String name, int initialHealth, SkillsSet skills, Iterable<PassiveSkill> passiveSkills, BattleRewardData onDeathReward) {
        super(name, initialHealth, skills, passiveSkills, onDeathReward);
    }

    @Override
    public void update(double currentTime) {
        for (ImmutableBattleSkill skill : skills) {
            if (skill.isAvailableAt(currentTime)) {
                super.applySkill(skill.getSkill(), currentTime);
                break;
            }
        }
    }

    Mob copy() {
        return new Mob(getName(), getInitialHealthAmount(), skills, getPassiveSkills(), getOnDeathReward());
    }

}
