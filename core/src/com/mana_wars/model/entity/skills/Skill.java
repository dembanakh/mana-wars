package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.GameItem;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Skill extends GameItem implements ReadableSkill {

    private final Iterable<SkillCharacteristic> skillCharacteristics;

    public Skill(int id, int level, Rarity rarity, String name, Iterable<SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name);
        this.skillCharacteristics = skillCharacteristics;
    }

    public void activate(BattleParticipant self, List<BattleParticipant> enemies) {
        for (SkillCharacteristic sc : skillCharacteristics) {
            if (sc.getTarget() >= 0)
                self.applySkillCharacteristic(sc, getLevel());

            if (sc.getTarget() < 0){
                for (int i=0, n=-sc.getTarget(); i < n && i < enemies.size(); i++){
                    BattleParticipant enemy = enemies.get(i);
                    if (enemy.isAlive())
                        enemy.applySkillCharacteristic(sc, getLevel());
                }
            }
            else if (sc.getTarget() > 0) {
                //TODO add implementation for allies
            }
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

    public Iterable<SkillCharacteristic> getCharacteristics() {
        List<SkillCharacteristic> result = new ArrayList<>();
        for (SkillCharacteristic sc : skillCharacteristics) {
            if (sc.isManaCost()) continue;
            result.add(sc);
        }
        return result;
    }

    private static Skill Empty = new Skill(50, 0, Rarity.EMPTY, "EMPTY",
            Collections.emptyList());

    public static Skill getEmpty() {
        return Empty;
    }

}
