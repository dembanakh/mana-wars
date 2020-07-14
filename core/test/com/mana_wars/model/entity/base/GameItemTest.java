package com.mana_wars.model.entity.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameItemTest {

    @Test
    public void testUpgradeLevel() {
        GameItem gameItem = new GameItem(1, 1, Rarity.COMMON, "a") {};
        gameItem.upgradeLevel();
        assertEquals(2, gameItem.getLevel());
    }

    @Test
    public void testUpgradeLevel_EdgeCase() {
        GameItem gameItem = new GameItem(1, 10, Rarity.COMMON, "a") {};
        gameItem.upgradeLevel();
        assertEquals(10, gameItem.getLevel());
    }

    @Test
    public void testDowngradeLevel() {
        GameItem gameItem = new GameItem(1, 3, Rarity.COMMON, "a") {};
        gameItem.downgradeLevel();
        assertEquals(2, gameItem.getLevel());
    }

    @Test
    public void testDowngradeLevel_EdgeCase() {
        GameItem gameItem = new GameItem(1, 1, Rarity.COMMON, "a") {};
        gameItem.downgradeLevel();
        assertEquals(1, gameItem.getLevel());
    }

    @Test
    public void testCompareTo_Rarity() {
        GameItem gameItem1 = new GameItem(1, 1, Rarity.COMMON, "a") {};
        GameItem gameItem2 = new GameItem(1, 1, Rarity.RARE, "a") {};
        assertTrue(gameItem1.compareTo(gameItem2) < 0);
    }

    @Test
    public void testCompareTo_Name() {
        GameItem gameItem1 = new GameItem(1, 1, Rarity.COMMON, "a") {};
        GameItem gameItem2 = new GameItem(1, 1, Rarity.COMMON, "b") {};
        assertTrue(gameItem1.compareTo(gameItem2) > 0);
    }

    @Test
    public void testCompareTo_Level() {
        GameItem gameItem1 = new GameItem(1, 1, Rarity.COMMON, "a") {};
        GameItem gameItem2 = new GameItem(1, 2, Rarity.COMMON, "a") {};
        assertTrue(gameItem1.compareTo(gameItem2) < 0);
    }
}