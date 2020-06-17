package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SkillCharacteristicTest {

    @Test
    public void getDescription() {
        SkillCharacteristic sc = new SkillCharacteristic(1, Characteristic.COOLDOWN,
                ValueChangeType.INCREASE, SkillCharacteristic.Target.ENEMY);
        assertEquals("ENEMY COOLDOWN +1", sc.getDescription());
    }
}