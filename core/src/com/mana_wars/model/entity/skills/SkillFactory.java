package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

import java.util.Random;

public class SkillFactory {
    static SkillList sl = new SkillList();

    public static Skill getNewSkill(){
        Rarity r = null;
        int rarityLen = Rarity.values().length;
        Random generator = new Random();
        int rand = generator.nextInt((int)Math.pow(2, rarityLen));
        int pow = (int)Math.pow(2, rarityLen-1);
        int barrier = pow;
        for(Rarity rar : Rarity.values()) {
            if (rand <= barrier){
                r = rar;
                break;
            }
            pow/=2;
            barrier+=pow;
        }
        rand = generator.nextInt(sl.skillsList.get(r).size());
        return sl.skillsList.get(r).get(rand);
    }

}
