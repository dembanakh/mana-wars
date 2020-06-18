package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.GameItem;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;

import java.util.ArrayList;
import java.util.List;

public class Skill extends GameItem {

    private final List<SkillCharacteristic> skillCharacteristics;

    public Skill(int id, int level, Rarity rarity, String name, List<SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name);
        this.skillCharacteristics = skillCharacteristics;
    }

    public void activate(BattleParticipant self, BattleParticipant enemy) {
        //System.out.println(getName() + " activated");
        for (SkillCharacteristic sc : skillCharacteristics) {
            if (sc.getTarget() == SkillCharacteristic.Target.SELF)
                self.applySkillCharacteristic(sc, getLevel());
            else if (sc.getTarget() == SkillCharacteristic.Target.ENEMY)
                enemy.applySkillCharacteristic(sc, getLevel());
        }
    }

    public int getManaCost() {
        for (SkillCharacteristic c : skillCharacteristics) {
            if (c.isManaCost()) {
                return c.getValue(1);
            }
        }
        return 0;
    }

    public String getDescription() {
        StringBuilder result = new StringBuilder();
        for (SkillCharacteristic sc : skillCharacteristics) {
            if (sc.getCharacteristic() == Characteristic.MANA) continue;
            result.append(sc.getDescription(getLevel()));
            result.append('\n');
        }
        return result.toString();
    }

    private static Skill Empty = new Skill(50, 0, Rarity.EMPTY, "EMPTY",
            new ArrayList<>());

    public static Skill getEmpty() {
        return Empty;
    }

}
