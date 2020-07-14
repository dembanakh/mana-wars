package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.battle.data.BattleRewardData;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.enemy.EnemyFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
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

    private BattleSummaryData summaryData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userSide = Collections.emptyList();
        enemySide = Collections.singletonList(mock(BattleParticipant.class));
        when(user.getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getOnDeathReward()).thenReturn(new BattleRewardData(1, 2, 3));
        EnemyFactory enemyFactory = mock(EnemyFactory.class);
        when(enemyFactory.generateEnemies()).thenReturn(enemySide);
        battle = new BattleWithRounds(user, userSide, enemyFactory, 2, observer);
        battle.init();
    }

    @Test
    public void testStart() {
        battle.start();
        verify(observer).setCurrentRound(0);
    }

    // How to test the other? We have no impact on the scheduler used inside BattleWithRounds

}