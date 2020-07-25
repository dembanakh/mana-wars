package com.mana_wars.model.entity.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpgradeFunctionTest {

    @Test
    public void testLinear() {
        UpgradeFunction func = UpgradeFunction.LINEAR;
        assertEquals(10, func.apply(10, 1, 1));
        assertEquals(20, func.apply(10, 2, 1));
        assertEquals(30, func.apply(10, 3, 1));
        assertEquals(15, func.apply(10, 2, 0.5));
        assertEquals(10, func.apply(10, 2, 0));
    }

}