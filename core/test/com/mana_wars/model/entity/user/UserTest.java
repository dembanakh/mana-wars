package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.repository.UserLevelExperienceRepository;
import com.mana_wars.model.repository.UserManaRepository;
import com.mana_wars.model.repository.UserSkillCasesRepository;
import com.mana_wars.model.repository.UsernameRepository;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTest {

    private static final CompositeDisposable disposable = new CompositeDisposable();

    private User user;

    @Mock
    private UserManaRepository userManaRepository;
    @Mock
    private UserLevelExperienceRepository userLevelExperienceRepository;
    @Mock
    private UsernameRepository usernameRepository;
    @Mock
    private UserSkillCasesRepository userSkillCasesRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(userManaRepository.getUserMana()).thenReturn(100);
        when(userLevelExperienceRepository.getUserLevel()).thenReturn(1);
        when(userLevelExperienceRepository.getUserLevelRequiredExperience()).thenReturn(Arrays.asList(0, 100, 400));
        when(usernameRepository.getUsername()).thenReturn("a");
        user = new User(userManaRepository, userLevelExperienceRepository,
                usernameRepository, userSkillCasesRepository);
    }

    @Test
    public void testPrepareBattleParticipant() {
        List<ActiveSkill> activeSkills = Collections.singletonList(mock(ActiveSkill.class));
        List<PassiveSkill> passiveSkills = Arrays.asList(mock(PassiveSkill.class), PassiveSkill.getEmpty());

        BattleParticipant bp = user.prepareBattleParticipant(activeSkills, passiveSkills);

        int size = 0;
        PassiveSkill passiveSkill = null;
        for (PassiveSkill skill : bp.getPassiveSkills()) {
            size++;
            passiveSkill = skill;
        }
        assertEquals(1, size);
        assertEquals(passiveSkills.get(0), passiveSkill);
    }

    @Test
    public void testUpdateManaAmount() {
        TestScheduler scheduler = new TestScheduler();
        AtomicInteger manaAmount = new AtomicInteger();
        disposable.add(user.getManaAmountObservable().observeOn(scheduler).subscribe(manaAmount::set));

        user.updateManaAmount(10);
        scheduler.triggerActions();

        verify(userManaRepository).setUserMana(110);
        assertEquals(110, manaAmount.get());
    }

    @Test
    public void testCheckNextLevel() {
        when(userLevelExperienceRepository.getCurrentUserExperience()).thenReturn(150);
        TestScheduler scheduler = new TestScheduler();
        AtomicInteger userLevel = new AtomicInteger();
        disposable.add(user.getLevelObservable().observeOn(scheduler).subscribe(userLevel::set));

        user.checkNextLevel();
        scheduler.triggerActions();

        verify(userLevelExperienceRepository).setUserLevel(2);
        assertEquals(2, userLevel.get());
    }

    @AfterClass
    public static void teardown() {
        disposable.dispose();
    }
}