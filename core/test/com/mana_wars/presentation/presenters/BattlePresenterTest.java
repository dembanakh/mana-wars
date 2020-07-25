package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.battle.participant.BattleParticipantData;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.presentation.view.BattleView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BattlePresenterTest {

    private BattlePresenter presenter;

    @Mock
    private BattleView view;
    @Mock
    private BattleInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new BattlePresenter(view, interactor, Runnable::run);
    }

    @Test
    public void testApplyUserSkill_Failure() {
        ActiveSkill skill = mock(ActiveSkill.class);
        when(interactor.tryApplyUserSkill(skill)).thenReturn(false);

        presenter.applyUserSkill(skill, 0);

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testApplyUserSkill_Success() {
        ActiveSkill skill = mock(ActiveSkill.class);
        when(interactor.tryApplyUserSkill(skill)).thenReturn(true);

        presenter.applyUserSkill(skill, 0);

        verify(view).blockSkills(0);
    }

    @Test
    public void testOnStartBattle() {
        ReadableBattleSummaryData data = mock(ReadableBattleSummaryData.class);
        when(interactor.getFinishBattleObservable()).thenAnswer((Answer<Single<ReadableBattleSummaryData>>)
                invocation -> Single.just(data));
        when(interactor.getEnemiesNumber()).thenReturn(2);

        presenter.onStartBattle();

        verify(view).startBattle(2);
        verify(view).finishBattle(data);
    }

    @Test
    public void testSetOpponents() {
        BattleParticipant user = mock(BattleParticipant.class);
        Subject<Integer> health = PublishSubject.create();
        when(user.getHealthObservable()).thenReturn(health);
        BattleParticipantData data = mock(BattleParticipantData.class);
        when(user.getData()).thenReturn(data);
        AtomicInteger viewHealth = new AtomicInteger();
        when(view.setUser(data)).thenAnswer((Answer<Consumer<? extends Integer>>) invocation -> viewHealth::set);
        when(user.getCurrentTarget()).thenReturn(2);

        presenter.setOpponents(user, Collections.emptyList());

        assertEquals(0, viewHealth.get());

        health.onNext(100);

        assertEquals(100, viewHealth.get());

        verify(view).cleanEnemies();
        verify(view).setEnemyCount(0);
        verify(view).setActiveEnemy(2);
    }

    @Test
    public void testSetEnemies() {
        BattleParticipant enemy = mock(BattleParticipant.class);
        Subject<Integer> health = PublishSubject.create();
        when(enemy.getHealthObservable()).thenReturn(health);
        BattleParticipantData data = mock(BattleParticipantData.class);
        when(enemy.getData()).thenReturn(data);
        AtomicInteger viewHealth = new AtomicInteger();
        when(view.addEnemy(data)).thenAnswer((Answer<Consumer<? extends Integer>>) invocation -> viewHealth::set);

        presenter.setEnemies(Collections.singletonList(enemy), 2);

        verify(view).cleanEnemies();
        verify(view).setEnemyCount(1);
        verify(view).setActiveEnemy(2);

        assertEquals(0, viewHealth.get());

        health.onNext(100);

        assertEquals(100, viewHealth.get());
    }

    @Test
    public void testChangeActiveEnemy() {
        when(interactor.changeUserTarget()).thenReturn(2);

        presenter.changeActiveEnemy();

        verify(view).setActiveEnemy(2);
    }

}