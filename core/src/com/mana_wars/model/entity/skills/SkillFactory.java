package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SkillFactory {

    public static Skill getNewSkill(Iterable<Skill> skills) {

        Map<Rarity, List<Skill>> skillsList = new HashMap<>();

        for (Rarity rarity : Rarity.values()) {
            if (rarity != Rarity.EMPTY) skillsList.put(rarity, new ArrayList<>());
        }

        for (Skill s : skills) {
            skillsList.get(s.getRarity()).add(s);
        }

        Rarity r = null;
        int rarityLen = Rarity.values().length - 1;
        Random generator = new Random();
        int rand = generator.nextInt((int) Math.pow(2, rarityLen));
        int pow = (int) Math.pow(2, rarityLen - 1);
        int barrier = pow;
        for (Rarity rar : Rarity.values()) {
            if (rar == Rarity.EMPTY) continue;
            if (rand <= barrier) {
                r = rar;
                break;
            }
            pow /= 2;
            barrier += pow;
        }
        rand = generator.nextInt(skillsList.get(r).size());
        return skillsList.get(r).get(rand);
    }

}
