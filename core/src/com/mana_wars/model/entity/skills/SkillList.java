package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillList {
    static Map<Rarity,List<Skill>> skillsList = createList();

    static Map<Rarity,List<Skill>> createList(){
        Map<Rarity, List<Skill>> result = new HashMap<>();
        ArrayList<Skill> commonList = new ArrayList<>();
        ArrayList<SkillCharacteristic> skillCharacteristicList = new ArrayList<>();
        skillCharacteristicList.add(new SkillCharacteristic(5, Characteristic.HEALTH, ValueChangeType.DECREASE, SkillCharacteristic.Target.ENEMY));
        commonList.add(new Skill(1,"",Rarity.COMMON,5,"attack", skillCharacteristicList));
        skillCharacteristicList.clear();
        skillCharacteristicList.add(new SkillCharacteristic(5, Characteristic.HEALTH, ValueChangeType.INCREASE, SkillCharacteristic.Target.SELF));
        commonList.add(new Skill(1,"",Rarity.COMMON,5,"cure", skillCharacteristicList));
        skillCharacteristicList.clear();
        result.put(Rarity.COMMON, commonList);

        ArrayList<Skill> rareList = new ArrayList<>();
        skillCharacteristicList.add(new SkillCharacteristic(7, Characteristic.HEALTH, ValueChangeType.DECREASE, SkillCharacteristic.Target.ENEMY));
        commonList.add(new Skill(1,"",Rarity.RARE,5,"attack", skillCharacteristicList));
        skillCharacteristicList.clear();
        skillCharacteristicList.add(new SkillCharacteristic(7, Characteristic.HEALTH, ValueChangeType.INCREASE, SkillCharacteristic.Target.SELF));
        commonList.add(new Skill(1,"",Rarity.RARE,5,"cure", skillCharacteristicList));
        result.put(Rarity.RARE, commonList);
        return result;
    }

}
