package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.user.UserMenuAPI;
import com.mana_wars.model.mana_bonus.ManaBonus;
import com.mana_wars.model.repository.DatabaseRepository;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.disposables.CompositeDisposable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainMenuInteractorTest {

    private static final CompositeDisposable disposable = new CompositeDisposable();

    private MainMenuInteractor interactor;

    @Mock
    private UserMenuAPI user;
    @Mock
    private DatabaseRepository databaseRepository;
    @Mock
    private ManaBonus manaBonus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new MainMenuInteractor(user, databaseRepository, manaBonus);
    }

    @Test
    public void testInitManaBonus() {
        when(manaBonus.getTimeSinceLastClaim()).thenReturn(100L);
        AtomicLong time = new AtomicLong();
        disposable.add(interactor.initManaBonus().subscribe(time::set));

        verify(manaBonus).init();
        assertEquals(100L, time.get());
    }

    @Test
    public void testClaimBonus() {
        when(manaBonus.evalCurrentBonus()).thenReturn(100);

        interactor.claimBonus();

        verify(user).updateManaAmount(100);
        verify(manaBonus).onBonusClaimed();
    }

    @Test
    public void testUseSkillCase() {
        interactor.useSkillCase();

        verify(user).updateSkillCases(-1);
    }

    @AfterClass
    public static void teardown() {
        disposable.dispose();
    }

}