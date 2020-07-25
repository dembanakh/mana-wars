package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BattleStateObserver;
import com.mana_wars.model.entity.battle.BattleStateObserverStarter;
import com.mana_wars.model.entity.battle.base.Battle;
import com.mana_wars.model.entity.battle.builder.BattleBuilder;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BattleInteractorTest {

    private BattleInteractor interactor;

    @Mock
    private UserBattleAPI user;
    @Mock
    private DatabaseRepository databaseRepository;
    @Mock
    private BattleStateObserverStarter battleStateObserver;

    @Mock
    private BattleParticipant userBP;
    @Mock
    private BattleParticipant enemyBP;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new BattleInteractor(user, databaseRepository);
        Battle battle = mock(Battle.class);
        when(battle.getUser()).thenReturn(userBP);
        when(battle.getEnemySide()).thenReturn(Collections.singletonList(enemyBP));
        when(battle.getFinishBattleObservable()).thenReturn(Single.just(new BattleSummaryData(0)));
        when(userBP.getCharacteristicValue(any())).thenReturn(100);
        when(user.getActiveSkills()).thenReturn(Collections.emptyList());
        BattleBuilder builder = new BattleBuilder() {
            private UserBattleAPI user;

            @Override
            public void setUser(UserBattleAPI user) {
                this.user = user;
            }

            @Override
            public Battle build(BattleStateObserver observer) {
                return battle;
            }

            @Override
            public void fetchData(CompositeDisposable disposable, DatabaseRepository databaseRepository, Runnable callback) {
                callback.run();
            }
        };
        interactor.init(battleStateObserver, builder);
    }

    @Test
    public void testInit() {
        verify(battleStateObserver).updateDurationCoefficients(100, 100);
        verify(battleStateObserver).setSkills(user.getActiveSkills());
        verify(battleStateObserver).onStartBattle();
    }

}