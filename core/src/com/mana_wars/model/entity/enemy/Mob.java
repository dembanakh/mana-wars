package com.mana_wars.model.entity.enemy;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mob extends BattleParticipant {

    Mob(int healthPoints) {
        super("Block-wolf", healthPoints);
        this.passiveSkills = new ArrayList<>();
    }

    @Override
    public void start() {
        super.start();
        double activationTime = 0;
        for (ActiveSkill skill : getInitialAutoSkills()) {
            activationTime += skill.getCastTime();
            //TODO change
            battle.requestSkillApplication(this, skill, activationTime);
        }

        System.out.println(getName() + " started");
    }

    @Override
    public void update(double currentTime) {

    }

    private List<ActiveSkill> getInitialAutoSkills() {
        return Arrays.asList(new ActiveSkill(1, 1, Rarity.COMMON,  1, 2, "block black",
                Arrays.asList(new SkillCharacteristic(10, Characteristic.HEALTH, ValueChangeType.DECREASE, SkillCharacteristic.Target.ENEMY))));
    }
}
