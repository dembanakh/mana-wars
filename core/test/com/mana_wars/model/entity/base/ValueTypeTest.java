package com.mana_wars.model.entity.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValueTypeTest {

    @Test
    public void testIncreaseValueApply() {
        assertEquals(110, ValueType.VALUE.apply(100, ValueChangeType.INCREASE, 10));
    }

    @Test
    public void testDecreaseValueApply() {
        assertEquals(90, ValueType.VALUE.apply(100, ValueChangeType.DECREASE, 10));
    }

    @Test
    public void testIncreasePercentApply() {
        assertEquals(220, ValueType.PERCENT.apply(200, ValueChangeType.INCREASE, 10));
    }

    @Test
    public void testDecreasePercentApply() {
        assertEquals(180, ValueType.PERCENT.apply(200, ValueChangeType.DECREASE, 10));
    }

}