package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.data.ReadableBattleRewardData;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.geq;
import static org.mockito.AdditionalMatchers.leq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BattleSummaryInteractorTest {

    private BattleSummaryInteractor interactor;

    @Mock
    private UserBattleSummaryAPI user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new BattleSummaryInteractor(user);

        ReadableBattleRewardData rewardData = mock(ReadableBattleRewardData.class);
        when(rewardData.getCaseProbabilityReward()).thenReturn(150);
        when(rewardData.getExperienceReward()).thenReturn(100);
        when(rewardData.getManaReward()).thenReturn(20);

        ReadableBattleSummaryData summaryData = mock(ReadableBattleSummaryData.class);
        when(summaryData.getRewardData()).thenReturn(rewardData);
        when(summaryData.getTime()).thenReturn(200d);

        interactor.parseSummaryData(summaryData);
    }

    @Test
    public void testParseSummaryData() {
        verify(user).updateManaAmount(20);
        verify(user).updateExperience(100);
        verify(user).updateSkillCases(and(geq(1), leq(2)));
    }

    @Test
    public void testGetManaReward() {
        assertEquals(20, interactor.getManaReward());
    }

    @Test
    public void testGetExperienceReward() {
        assertEquals(100, interactor.getExperienceReward());
    }

    @Test
    public void testGetGainedSkillCases() {
        int cases = interactor.getGainedSkillCases();
        assertTrue(1 <= cases && cases <= 2);
    }

    @Test
    public void testGetBattleDuration() {
        assertEquals(200, interactor.getBattleDuration(), 0.001);
    }

}