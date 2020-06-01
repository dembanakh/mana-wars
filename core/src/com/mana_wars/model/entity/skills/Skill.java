package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.base.GameItem;
import com.mana_wars.model.entity.base.Rarity;

import java.util.ArrayList;
import java.util.List;

public class Skill extends GameItem {

    protected int manaCost;
    protected List <SkillCharacteristic> skillCharacteristics;

    public Skill(int id, int level, Rarity rarity, String name, int manaCost, List <SkillCharacteristic> skillCharacteristics) {
        super(id, level, rarity, name);
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

    //TODO generate description based on characteristics
    public List<String> getDescription() {
        List<String> result= new ArrayList<>();
        for(SkillCharacteristic sc : skillCharacteristics){
            result.add( String.valueOf(sc.getDescription()));
            System.out.println(sc.getDescription());
        }
        return result;
    }

    public static Skill Empty = new Skill(50, 0, Rarity.EMPTY, "EMPTY",
            0, new ArrayList<>());

}
