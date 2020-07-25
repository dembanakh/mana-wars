package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.interactor.BattleSummaryInteractor;
import com.mana_wars.presentation.view.BattleSummaryView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BattleSummaryPresenterTest {

    private BattleSummaryPresenter presenter;

    @Mock
    private BattleSummaryView view;
    @Mock
    private BattleSummaryInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new BattleSummaryPresenter(view, interactor, Runnable::run);
    }

    @Test
    public void testParseSummaryData() {
        ReadableBattleSummaryData data = mock(ReadableBattleSummaryData.class);
        when(interactor.getBattleDuration()).thenReturn(100d);
        when(interactor.getManaReward()).thenReturn(10);
        when(interactor.getExperienceReward()).thenReturn(20);
        when(interactor.getGainedSkillCases()).thenReturn(2);
        when(interactor.getParticipantsStatistics()).thenReturn(Collections.emptyMap());

        presenter.parseSummaryData(data);

        verify(interactor).parseSummaryData(data);
        verify(view).setBattleDuration(100d);
        verify(view).setManaReward(10);
        verify(view).setExperienceReward(20);
        verify(view).setSkillCasesReward(2);
        verify(view).setParticipantsStatistics(Collections.emptySet());
    }

}