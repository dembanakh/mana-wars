package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillList {
    /*static Map<Rarity,List<Skill>> skillsList = createList();

    private static Map<Rarity, List<Skill>> createList(){
        Map<Rarity, List<Skill>> result = new HashMap<>();

        ArrayList<Skill> commonList = new ArrayList<>(
                Arrays.asList(
                        new ActiveSkill(1, 1, Rarity.COMMON, 10, 4,"Magic spear",
                                new ArrayList<>(Arrays.asList(new SkillCharacteristic(10, Characteristic.HEALTH, ValueChangeType.DECREASE, SkillCharacteristic.Target.ENEMY)))),
                        new ActiveSkill(2, 1, Rarity.COMMON, 10, 5,"Self-healing",
                                new ArrayList<>(Arrays.asList(new SkillCharacteristic(5, Characteristic.HEALTH, ValueChangeType.INCREASE, SkillCharacteristic.Target.SELF)))),
                        new PassiveSkill(3, 1, Rarity.COMMON, 50, "Concentration",
                                new ArrayList<>(Arrays.asList(new SkillCharacteristic(10,Characteristic.COOLDOWN, ValueChangeType.DECREASE,SkillCharacteristic.Target.SELF))))
                        )
        );
        result.put(Rarity.COMMON, commonList);

        ArrayList<Skill> rareList = new ArrayList<>(
                Arrays.asList(
                        new ActiveSkill(4, 1, Rarity.RARE, 20, 10, "Life steal",
                                new ArrayList<>(Arrays.asList(
                                        new SkillCharacteristic(20, Characteristic.HEALTH, ValueChangeType.DECREASE, SkillCharacteristic.Target.ENEMY),
                                        new SkillCharacteristic(10, Characteristic.HEALTH,ValueChangeType.INCREASE, SkillCharacteristic.Target.SELF))))
                )
        );
        result.put(Rarity.RARE, rareList);

        return result;
    }
    */
}
