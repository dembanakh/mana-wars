package com.mana_wars.model.entity.battle.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class BattleRewardDataTest {

    @Test
    public void testAdd() {
        BattleRewardData data1 = new BattleRewardData(0, 0, 0);
        BattleRewardData data2 = new BattleRewardData(1, 2, 3);

        data1.add(data2);

        assertEquals(1, data1.getManaReward());
        assertEquals(2, data1.getExperienceReward());
        assertEquals(3, data1.getCaseProbabilityReward());
    }

}