package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.base.GameItem;
import com.mana_wars.model.entity.base.Rarity;

import java.util.List;

public class Skill extends GameItem {

    protected int mana_cost;
    protected String description;
    protected List <SkillCharacteristic> skillCharacteristics;

    public Skill(int level, String iconPath, Rarity rarity, int mana_cost, String description) {
        super(level, iconPath, rarity);
        this.mana_cost = mana_cost;
        this.description = description;
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

}
