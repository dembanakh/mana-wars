package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.MainMenuView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class MainMenuPresenterTest {

    private MainMenuPresenter presenter;

    @Mock
    private MainMenuView view;
    @Mock
    private MainMenuInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainMenuPresenter(view, interactor, Runnable::run);
    }

    @Test
    public void testOnScreenShow() {
        when(interactor.initManaBonus()).thenAnswer((Answer<Single<Long>>) invocation -> Single.just(100L));
        when(interactor.isBonusAvailable()).thenReturn(true);
        when(interactor.getUsername()).thenAnswer((Answer<Single<String>>) invocation -> Single.just("a"));

        presenter.onScreenShow();

        verify(view).setTimeSinceLastManaBonusClaimed(100L);
        verify(view).onManaBonusReady();
        verify(view).setUsername("a");
    }

    @Test
    public void onOpenSkillCase_Failure() {
        when(interactor.getUserSkillCasesNumber()).thenReturn(0);

        presenter.onOpenSkillCase();

        verifyNoMoreInteractions(view);
    }

    @Test
    public void onOpenSkillCase_Success() {
        Skill skill = mock(Skill.class);
        when(interactor.getUserSkillCasesNumber()).thenReturn(1);
        when(interactor.useSkillCase()).thenReturn(0);
        when(interactor.getNewSkill()).thenAnswer((Answer<Single<Skill>>) invocation -> Single.just(skill));

        presenter.onOpenSkillCase();

        verify(view).setSkillCasesNumber(0);
        verify(view).openSkillCaseWindow(skill);
    }

    @Test
    public void testSyncManaBonusTime() {
        when(interactor.getTimeSinceLastManaBonusClaim()).thenReturn(100L);
        when(interactor.isBonusAvailable()).thenReturn(true);

        presenter.synchronizeManaBonusTime();

        verify(view).setTimeSinceLastManaBonusClaimed(100L);
        verify(view).onManaBonusReady();
    }

    @Test
    public void testRefreshSkillCasesNumber() {
        when(interactor.getUserSkillCasesNumber()).thenReturn(2);

        presenter.refreshSkillCasesNumber();

        verify(view).setSkillCasesNumber(2);
    }

}