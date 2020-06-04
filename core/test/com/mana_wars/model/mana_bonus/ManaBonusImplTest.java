package com.mana_wars.model.mana_bonus;

import com.mana_wars.model.repository.ManaBonusRepository;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ManaBonusImplTest {

    @Test
    public void init_firstBonus() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(false);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 1, manaBonusRepository);

        manaBonus.init();

        verify(manaBonusRepository).setLastTimeBonusClaimed(1);
    }

    @Test
    public void init_notFirstBonus() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 1, manaBonusRepository);

        manaBonus.init();

        verify(manaBonusRepository, times(0)).setLastTimeBonusClaimed(1);
    }

    @Test
    public void init_twice() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(false);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 1, manaBonusRepository);

        manaBonus.init();
        verify(manaBonusRepository).wasBonusEverClaimed();
        verify(manaBonusRepository).setLastTimeBonusClaimed(1);
        manaBonus.init();

        verify(manaBonusRepository).wasBonusEverClaimed();
        verifyNoMoreInteractions(manaBonusRepository);
    }

    @Test
    public void getTimeSinceLastClaim() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(2L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 10, manaBonusRepository);
        manaBonus.init();

        assertEquals(8L, manaBonus.getTimeSinceLastClaim());
    }

    @Test
    public void isBonusBitAvailable_true() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(10000L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 70000, manaBonusRepository);
        manaBonus.init();

        assertTrue(manaBonus.isBonusBitAvailable());
    }

    @Test
    public void isBonusBitAvailable_false() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(10000L);

        ManaBonus manaBonus = new ManaBonusImpl(2, 1, 1,
                () -> 70000, manaBonusRepository);
        manaBonus.init();

        assertFalse(manaBonus.isBonusBitAvailable());
    }

    @Test
    public void onBonusClaimed_zero() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(2L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 5, 4,
                () -> 10, manaBonusRepository);
        manaBonus.init();

        final int[] backValue = {1};
        manaBonus.onBonusClaimed(x -> backValue[0] = x);

        verify(manaBonusRepository).setLastTimeBonusClaimed(10);
        assertEquals(0, backValue[0]);
    }

    @Test
    public void onBonusClaimed() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(10L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 5, 4,
                () -> 10 + (2 * 60 * 1000 + 1), manaBonusRepository);
        manaBonus.init();

        final int[] backValue = {1};
        manaBonus.onBonusClaimed(x -> backValue[0] = x);

        verify(manaBonusRepository).setLastTimeBonusClaimed(10 + (2 * 60 * 1000 + 1));
        assertEquals(10, backValue[0]);
    }

    @Test
    public void getFullBonusTimeout() {
        ManaBonusRepository manaBonusRepository = mock(ManaBonusRepository.class);
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);

        ManaBonus manaBonus = new ManaBonusImpl(10, 20, 30,
                () -> 10, manaBonusRepository);
        manaBonus.init();

        assertEquals(300, manaBonus.getFullBonusTimeout());
    }
}