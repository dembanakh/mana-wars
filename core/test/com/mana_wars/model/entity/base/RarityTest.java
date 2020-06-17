package com.mana_wars.model.entity.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RarityTest {

    @Test
    public void testGetRarityByID() {
        assertEquals(Rarity.COMMON, Rarity.getRarityByID(1));
        assertEquals(Rarity.RARE, Rarity.getRarityByID(2));
        assertEquals(Rarity.ARCANE, Rarity.getRarityByID(3));
        assertEquals(Rarity.HEROIC, Rarity.getRarityByID(4));
        assertEquals(Rarity.EPIC, Rarity.getRarityByID(5));
        assertEquals(Rarity.LEGENDARY, Rarity.getRarityByID(6));
    }
}