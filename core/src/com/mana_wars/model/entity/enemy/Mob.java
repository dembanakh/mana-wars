package com.mana_wars.model.entity.enemy;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import java.util.List;

public class Mob extends BattleParticipant {

    public Mob(String name, int initialHealth, Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills) {
        super(name, initialHealth, activeSkills, passiveSkills);
    }

    private Mob(String name, int initialHealth, List<BattleSkill> battleSkills, Iterable<PassiveSkill> passiveSkills) {
        super(name, initialHealth, battleSkills, passiveSkills);
    }

    @Override
    public void update(double currentTime) {
        for (BattleSkill skill : battleSkills) {
            if (skill.isAvailableAt(currentTime)) {
                super.applySkill(skill.skill, currentTime);
                break;
            }
        }
    }

    Mob copy() {
        return new Mob(getName(), getInitialHealthAmount(), battleSkills, passiveSkills);
    }

}
