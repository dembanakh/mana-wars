package com.mana_wars.model.entity.battle.participant;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattleParticipantTargetTest {

    private BattleParticipantTarget target;

    @Mock
    private BattleClientAPI battle;

    @Mock
    private BattleParticipant bp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        target = new BattleParticipantTarget(bp);

        BattleParticipant other = mock(BattleParticipant.class);
        when(other.isAlive()).thenReturn(true);
        when(battle.getOpponents(bp)).thenReturn(Collections.singletonList(other));
        target.change(battle);
    }

    @Test
    public void testInitialTarget() {
        assertEquals(0, target.getCurrent());
    }

    @Test
    public void testChange1Choice() {
        BattleParticipant other1 = mock(BattleParticipant.class);
        when(other1.isAlive()).thenReturn(true);
        BattleParticipant other2 = mock(BattleParticipant.class);
        when(other2.isAlive()).thenReturn(true);
        when(battle.getOpponents(bp)).thenReturn(Arrays.asList(other1, other2));
        target.change(battle);

        assertEquals(1, target.getCurrent());
    }

    @Test
    public void testChangeDead() {
        BattleParticipant other1 = mock(BattleParticipant.class);
        when(other1.isAlive()).thenReturn(true);
        BattleParticipant other2 = mock(BattleParticipant.class);
        when(other2.isAlive()).thenReturn(false);
        when(battle.getOpponents(bp)).thenReturn(Arrays.asList(other1, other2));
        target.change(battle);

        assertEquals(0, target.getCurrent());
    }

}