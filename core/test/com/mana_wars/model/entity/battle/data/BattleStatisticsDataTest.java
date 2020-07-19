package com.mana_wars.model.entity.battle.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BattleStatisticsDataTest {

    private BattleStatisticsData statisticsData;

    @Before
    public void setup() {
        statisticsData = new BattleStatisticsData();
    }

    @Test
    public void testUpdateSource_PosPos() {
        statisticsData.updateValuesAsSourceSelf(10);
        statisticsData.updateValuesAsSourceTarget(20);
        assertEquals(30, statisticsData.getSelfHealing());
    }

    @Test
    public void testUpdateSource_PosNeg() {
        statisticsData.updateValuesAsSourceSelf(10);
        statisticsData.updateValuesAsSourceTarget(-20);
        assertEquals(10, statisticsData.getSelfHealing());
        assertEquals(20, statisticsData.getCausedDamage());
    }

    @Test
    public void testUpdateSource_NegPos() {
        statisticsData.updateValuesAsSourceSelf(-10);
        statisticsData.updateValuesAsSourceTarget(20);
        assertEquals(20, statisticsData.getSelfHealing());
        assertEquals(10, statisticsData.getReceivedDamage());
    }

    @Test
    public void testUpdateSource_NegNeg() {
        statisticsData.updateValuesAsSourceSelf(-10);
        statisticsData.updateValuesAsSourceTarget(-20);
        assertEquals(20, statisticsData.getCausedDamage());
        assertEquals(10, statisticsData.getReceivedDamage());
    }

    @Test
    public void testUpdateTarget_Pos() {
        statisticsData.updateValuesAsTarget(10);
        assertEquals(10, statisticsData.getReceivedHealing());
    }

    @Test
    public void testUpdateTarget_Neg() {
        statisticsData.updateValuesAsTarget(-10);
        assertEquals(10, statisticsData.getReceivedDamage());
    }

}