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

    Mob(int healthPoints, int manaPoints) {
        super("Block-wolf", healthPoints, manaPoints);
        this.passiveSkills = new ArrayList<>();
    }

    @Override
    public void start() {
        double activationTime = 0;
        for (ActiveSkill skill : getInitialAutoSkills()) {
            activationTime += skill.getCastTime();
            battle.requestSkillApplication(this, skill, activationTime);
        }

        System.out.println(getName() + " started");
    }

    @Override
    public void act(float delta) {

    }

    private List<ActiveSkill> getInitialAutoSkills() {
        return Arrays.asList(new ActiveSkill(1, 1, Rarity.COMMON, 0, 1, 2, "",
                Arrays.asList(new SkillCharacteristic(10, Characteristic.HEALTH, ValueChangeType.DECREASE, SkillCharacteristic.Target.ENEMY))));
    }
}
