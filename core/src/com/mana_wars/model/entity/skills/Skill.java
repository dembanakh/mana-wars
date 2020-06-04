package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.base.GameItem;
import com.mana_wars.model.entity.base.Rarity;

import java.util.ArrayList;
import java.util.List;

public class Skill extends GameItem {

    private int manaCost;
    private List <SkillCharacteristic> skillCharacteristics;

    public Skill(int id, int level, Rarity rarity, String name, int manaCost, List <SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name);
        this.manaCost = manaCost;
        this.skillCharacteristics = skillCharacteristics;
    }

    //TODO rewrite and add observers
    public void activate(BattleParticipant self, BattleParticipant enemy){
        for(SkillCharacteristic sc : skillCharacteristics){
            if(sc.getTarget()==SkillCharacteristic.Target.SELF)
                self.applySkillCharacteristic(sc);
            else if(sc.getTarget()==SkillCharacteristic.Target.ENEMY)
                enemy.applySkillCharacteristic(sc);
        }
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getDescription() {
        StringBuilder result = new StringBuilder();
        for(SkillCharacteristic sc : skillCharacteristics){
            result.append(sc.getDescription());
            result.append('\n');
        }
        return result.toString();
    }

    private static Skill Empty = new Skill(50, 0, Rarity.EMPTY, "EMPTY",
            0, new ArrayList<>());

    public static Skill getEmpty() {
        return Empty;
    }
}
