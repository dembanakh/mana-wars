package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SkillCharacteristicTest {

    private SkillCharacteristic sc;

    @Before
    public void setup() {
        sc = new SkillCharacteristic(2, Characteristic.COOLDOWN,
                ValueChangeType.INCREASE, SkillCharacteristic.Target.ENEMY,
                UpgradeFunction.LINEAR, 1);
    }

    @Test
    public void testGetDescription() {
        assertEquals("ENEMY COOLDOWN +2", sc.getDescription(1));
        assertEquals("ENEMY COOLDOWN +4", sc.getDescription(2));
    }

    @Test
    public void testGetValue() {
        assertEquals(2, sc.getValue(1));
        assertEquals(4, sc.getValue(2));
    }

    @Test
    public void testIsManaCost_No() {
        assertFalse(sc.isManaCost());
    }

    @Test
    public void testIsManaCost_Yes() {
        SkillCharacteristic characteristic = new SkillCharacteristic(2, Characteristic.MANA,
                ValueChangeType.DECREASE, SkillCharacteristic.Target.SELF,
                UpgradeFunction.LINEAR, 0);
        assertTrue(characteristic.isManaCost());
    }

}