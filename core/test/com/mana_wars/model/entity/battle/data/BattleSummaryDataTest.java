package com.mana_wars.model.entity.battle.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BattleSummaryDataTest {

    private BattleSummaryData data;

    @Before
    public void setup() {
        data = new BattleSummaryData();
    }

    @Test
    public void testAddReward() {
        data.addReward(new BattleRewardData(1, 2, 3));

        assertEquals(1, data.getRewardData().getManaReward());
        assertEquals(2, data.getRewardData().getExperienceReward());
        assertEquals(3, data.getRewardData().getCaseProbabilityReward());
    }

    @Test
    public void testCombineWith() {
        BattleSummaryData other = new BattleSummaryData();
        other.addReward(new BattleRewardData(1, 2, 3));

        data.combineWith(other);

        assertEquals(1, data.getRewardData().getManaReward());
        assertEquals(2, data.getRewardData().getExperienceReward());
        assertEquals(3, data.getRewardData().getCaseProbabilityReward());
    }

}