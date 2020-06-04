package com.mana_wars.model.mana_bonus;

import com.mana_wars.model.repository.ManaBonusRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ManaBonusImplTest {

    @Mock
    private ManaBonusRepository manaBonusRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void init_firstBonus() {
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(false);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 1, manaBonusRepository);

        manaBonus.init();

        verify(manaBonusRepository).setLastTimeBonusClaimed(1);
    }

    @Test
    public void init_notFirstBonus() {
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 1, manaBonusRepository);

        manaBonus.init();

        verify(manaBonusRepository, times(0)).setLastTimeBonusClaimed(1);
    }

    @Test
    public void init_twice() {
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
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(2L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 10, manaBonusRepository);
        manaBonus.init();

        assertEquals(8L, manaBonus.getTimeSinceLastClaim());
    }

    @Test
    public void isBonusBitAvailable_true() {
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(10000L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 1, 1,
                () -> 70000, manaBonusRepository);
        manaBonus.init();

        assertTrue(manaBonus.isBonusBitAvailable());
    }

    @Test
    public void isBonusBitAvailable_false() {
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(10000L);

        ManaBonus manaBonus = new ManaBonusImpl(2, 1, 1,
                () -> 70000, manaBonusRepository);
        manaBonus.init();

        assertFalse(manaBonus.isBonusBitAvailable());
    }

    @Test
    public void evalCurrentBonus_zero() {
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(2L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 5, 4,
                () -> 10, manaBonusRepository);
        manaBonus.init();

        assertEquals(0, manaBonus.evalCurrentBonus());
    }

    @Test
    public void evalCurrentBonus() {
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(10L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 5, 4,
                () -> 10 + (2 * 60 * 1000 + 1), manaBonusRepository);
        manaBonus.init();

        assertEquals(10, manaBonus.evalCurrentBonus());
    }

    @Test
    public void onBonusClaimed() {
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);
        when(manaBonusRepository.getLastTimeBonusClaimed()).thenReturn(10L);

        ManaBonus manaBonus = new ManaBonusImpl(1, 5, 4,
                () -> 10 + (2 * 60 * 1000 + 1), manaBonusRepository);
        manaBonus.init();
        manaBonus.onBonusClaimed();

        verify(manaBonusRepository).setLastTimeBonusClaimed(10 + (2 * 60 * 1000 + 1));
    }

    @Test
    public void getFullBonusTimeout() {
        when(manaBonusRepository.wasBonusEverClaimed()).thenReturn(true);

        ManaBonus manaBonus = new ManaBonusImpl(10, 20, 30,
                () -> 10, manaBonusRepository);
        manaBonus.init();

        assertEquals(300, manaBonus.getFullBonusTimeout());
    }
}