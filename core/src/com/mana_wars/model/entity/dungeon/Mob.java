package com.mana_wars.model.entity.dungeon;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ImmutableBattleSkill;

class Mob extends BattleParticipant {

    Mob(MobBlueprint mob) {
        super(mob.getName(), mob.getIconID(), mob.getInitialHealth(),
                mob.getActiveSkills(), mob.getPassiveSkills(),
                mob.getManaReward(), mob.getExperienceReward(), mob.getCaseProbabilityReward());
    }

    @Override
    public void update(double currentTime) {
        for (ImmutableBattleSkill skill : skills.elements()) {
            if (skill.isAvailableAt(currentTime)) {
                super.applySkill(skill.getSkill(), currentTime);
                break;
            }
        }
    }
}
