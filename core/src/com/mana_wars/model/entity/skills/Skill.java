package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.base.GameItem;
import com.mana_wars.model.entity.base.Rarity;

import java.util.List;
import java.util.Locale;

public class Skill extends GameItem {

    protected int manaCost;
    protected String name;
    protected List <SkillCharacteristic> skillCharacteristics;

    public Skill(int id ,int level, Rarity rarity, int manaCost, String name, List <SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity);
        this.manaCost = manaCost;
        this.name = name;
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

    public String getName(){
        return name;
    }

    //TODO generate description based on characteristics
    public String getDescription() {
        return String.valueOf(manaCost);
    }

}
