package com.mana_wars.model.entity.battle.data;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattleSummaryDataTest {

    private BattleSummaryData data;

    @Before
    public void setup() {
        data = new BattleSummaryData(0);
    }

    @Test
    public void testAddReward() {
        data.addReward(new BattleRewardData(1, 2, 3));

        assertEquals(1, data.getRewardData().getManaReward());
        assertEquals(2, data.getRewardData().getExperienceReward());
        assertEquals(3, data.getRewardData().getCaseProbabilityReward());
    }

    @Test
    public void testAddStatistics() {
        BattleParticipant bp = mock(BattleParticipant.class);
        BattleStatisticsData bpData = mock(BattleStatisticsData.class);
        when(bp.getBattleStatisticsData()).thenReturn(bpData);
        data.addStatisticsFrom(bp);

        assertEquals(bpData, data.getParticipantsStatistics().get(bp));
    }

    @Test
    public void testCombineWith() {
        BattleSummaryData other = new BattleSummaryData(0);
        other.addReward(new BattleRewardData(1, 2, 3));
        BattleParticipant bp = mock(BattleParticipant.class);
        BattleStatisticsData bpData = mock(BattleStatisticsData.class);
        when(bp.getBattleStatisticsData()).thenReturn(bpData);
        other.addStatisticsFrom(bp);

        data.combineWith(other);

        assertEquals(1, data.getRewardData().getManaReward());
        assertEquals(2, data.getRewardData().getExperienceReward());
        assertEquals(3, data.getRewardData().getCaseProbabilityReward());
        assertEquals(bpData, data.getParticipantsStatistics().get(bp));
    }

}