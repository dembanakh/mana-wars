package com.mana_wars.model.entity;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;

public class User extends BattleParticipant {

    public User() {
        super("User", 1000, 1000);
    }


    @Override
    public void start() {

    }

    @Override
    public void act(float delta) {

    }

    public User reInitCharacteristics() {
        return this;
    }

    public void tryApplyActiveSkill(ActiveSkill skill) {
        if (getCharacteristicValue(Characteristic.MANA) >= skill.getManaCost()) {
            battle.requestSkillApplication(this, skill);
            // reduce user's mana amount
        }
    }
}
