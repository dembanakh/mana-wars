package com.mana_wars.model.entity.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharacteristicTest {

    @Test
    public void testChangeValue_Value() {
        Characteristic ch = Characteristic.HEALTH;
        int value = 100;
        int result = ch.changeValue(value, ValueChangeType.INCREASE, 10);
        assertEquals(110, result);
    }

    @Test
    public void testChangeValue_Percent() {
        Characteristic ch = Characteristic.COOLDOWN;
        int value = 200;
        int result = ch.changeValue(value, ValueChangeType.INCREASE, 10);
        assertEquals(220, result);
    }

    @Test
    public void testChangeValue_Value_LowerBound() {
        Characteristic ch = Characteristic.HEALTH;
        int value = 100;
        int result = ch.changeValue(value, ValueChangeType.DECREASE, 110);
        assertEquals(0, result);
    }

    @Test
    public void testChangeValue_Percent_LowerBound() {
        Characteristic ch = Characteristic.COOLDOWN;
        int value = 50;
        int result = ch.changeValue(value, ValueChangeType.DECREASE, 90);
        assertEquals(10, result);
    }

    @Test
    public void testValidateValue() {
        Characteristic ch = Characteristic.HEALTH;
        int value = 100;
        int result = ch.changeValue(value, ValueChangeType.DECREASE, 200);
        assertEquals(0, result);
    }

    @Test
    public void testGetCharacteristicById() {
        assertEquals(Characteristic.HEALTH, Characteristic.getCharacteristicById(1));
        assertEquals(Characteristic.MANA, Characteristic.getCharacteristicById(2));
        assertEquals(Characteristic.COOLDOWN, Characteristic.getCharacteristicById(3));
        assertEquals(Characteristic.CAST_TIME, Characteristic.getCharacteristicById(4));
    }
}