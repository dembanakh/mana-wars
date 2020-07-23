package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.data.BattleRewardData;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.enemy.EnemyFactory;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BattleWithRoundsTest {

    private BattleWithRounds battle;

    @Mock
    private BattleStateObserver observer;

    @Mock
    private BattleParticipant user;
    private List<BattleParticipant> userSide;
    private List<BattleParticipant> enemySide;

    private ReadableBattleSummaryData summaryData;

    private static final CompositeDisposable disposable = new CompositeDisposable();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userSide = Collections.emptyList();
        enemySide = Collections.singletonList(mock(BattleParticipant.class));
        when(user.getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getPassiveSkills()).thenAnswer((Answer<Iterable<? extends PassiveSkill>>)
                invocation -> Collections.singletonList(
                    new PassiveSkill(1, 1, Rarity.COMMON, "a", Collections.singletonList(
                        new SkillCharacteristic(5, Characteristic.COOLDOWN,
                                ValueChangeType.DECREASE, -1, UpgradeFunction.LINEAR, 1)
                ))));
        when(enemySide.get(0).getOnDeathReward()).thenReturn(new BattleRewardData(1, 2, 3));
        EnemyFactory enemyFactory = mock(EnemyFactory.class);
        when(enemyFactory.generateEnemies()).thenReturn(enemySide);
        battle = new BattleWithRounds(user, userSide, enemyFactory, 2, observer);
        battle.init();
    }

    @Test
    public void testStart() {
        battle.start();
        verify(observer).setCurrentRound(1);
    }

    @Test
    public void testChangeRound_UserAlive() {
        when(enemySide.get(0).isAlive()).thenReturn(false);
        when(user.isAlive()).thenReturn(true);
        battle.start();
        verify(observer).setCurrentRound(1);
        battle.update(1);
        verify(observer).setCurrentRound(2);
        verify(user).setCharacteristicValue(Characteristic.CAST_TIME, 100);
        verify(user).setCharacteristicValue(Characteristic.COOLDOWN, 100);
        verify(user, atLeastOnce()).setCharacteristicApplicationMode(any());
        verify(observer).updateDurationCoefficients(anyInt(), anyInt());
        verify(observer).setEnemies(enemySide, 0);
    }

    @Test
    public void testChangeRound_UserDead() {
        when(enemySide.get(0).isAlive()).thenReturn(true);
        when(user.isAlive()).thenReturn(false);
        disposable.add(battle.getFinishBattleObservable().subscribe(bsd -> summaryData = bsd));
        battle.start();
        verify(observer).setCurrentRound(1);
        battle.update(1);
        assertNotNull(summaryData);
    }

    @Test
    public void testChangeRound_LastRound() {
        when(enemySide.get(0).isAlive()).thenReturn(false);
        when(user.isAlive()).thenReturn(true);
        disposable.add(battle.getFinishBattleObservable().subscribe(bsd -> summaryData = bsd));
        battle.start();
        verify(observer).setCurrentRound(1);
        battle.update(1);
        verify(observer).setCurrentRound(2);
        when(enemySide.get(0).isAlive()).thenReturn(true);
        when(user.isAlive()).thenReturn(false);
        battle.update(1);
        assertNotNull(summaryData);
    }

    @After
    public void restore() {
        summaryData = null;
    }

    @AfterClass
    public static void teardown() {
        disposable.dispose();
    }

}