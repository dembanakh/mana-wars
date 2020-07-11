package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.ValueChangeType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharacteristicTest {

    @Test
    public void testChangeValue_Value() {
        com.mana_wars.model.entity.base.Characteristic ch = com.mana_wars.model.entity.base.Characteristic.HEALTH;
        int value = 100;
        int result = ch.changeValue(value, ValueChangeType.INCREASE, 10);
        assertEquals(110, result);
    }

    @Test
    public void testChangeValue_Percent() {
        com.mana_wars.model.entity.base.Characteristic ch = com.mana_wars.model.entity.base.Characteristic.COOLDOWN;
        int value = 200;
        int result = ch.changeValue(value, ValueChangeType.INCREASE, 10);
        assertEquals(220, result);
    }

    @Test
    public void testChangeValue_Value_LowerBound() {
        com.mana_wars.model.entity.base.Characteristic ch = com.mana_wars.model.entity.base.Characteristic.HEALTH;
        int value = 100;
        int result = ch.changeValue(value, ValueChangeType.DECREASE, 110);
        assertEquals(0, result);
    }

    @Test
    public void testChangeValue_Percent_LowerBound() {
        com.mana_wars.model.entity.base.Characteristic ch = com.mana_wars.model.entity.base.Characteristic.COOLDOWN;
        int value = 50;
        int result = ch.changeValue(value, ValueChangeType.DECREASE, 90);
        assertEquals(10, result);
    }

    @Test
    public void testValidateValue() {
        com.mana_wars.model.entity.base.Characteristic ch = com.mana_wars.model.entity.base.Characteristic.HEALTH;
        int value = 100;
        int result = ch.changeValue(value, ValueChangeType.DECREASE, 200);
        assertEquals(0, result);
    }

    @Test
    public void testGetCharacteristicById() {
        assertEquals(com.mana_wars.model.entity.base.Characteristic.HEALTH, com.mana_wars.model.entity.base.Characteristic.getCharacteristicById(1));
        assertEquals(com.mana_wars.model.entity.base.Characteristic.MANA, com.mana_wars.model.entity.base.Characteristic.getCharacteristicById(2));
        assertEquals(com.mana_wars.model.entity.base.Characteristic.COOLDOWN, com.mana_wars.model.entity.base.Characteristic.getCharacteristicById(3));
        assertEquals(com.mana_wars.model.entity.base.Characteristic.CAST_TIME, Characteristic.getCharacteristicById(4));
    }
}