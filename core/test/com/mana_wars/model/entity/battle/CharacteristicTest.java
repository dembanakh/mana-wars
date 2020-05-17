package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.ValueChangeType;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CharacteristicTest {

    @Test
    public void testChangeValue() {
        Characteristic ch = Characteristic.HEALTH;
        ch.initValue(100);
        ch.changeValue(ValueChangeType.INCREASE, 10);
        assertEquals(110, ch.getValue());

    }

    @Test
    public void testValidateValue(){
        Characteristic ch = Characteristic.HEALTH;
        ch.initValue(100);
        ch.changeValue(ValueChangeType.DECREASE, 200);
        assertEquals(0, ch.getValue());
    }
}