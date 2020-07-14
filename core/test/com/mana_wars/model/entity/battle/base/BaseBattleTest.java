package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.data.BattleRewardData;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseBattleTest {

    private static CompositeDisposable disposable = new CompositeDisposable();

    private BaseBattle battle;

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
        battle = new BaseBattle(user, userSide, enemySide);
    }

    @Test
    public void testInit() {
        PassiveSkill skill1 = mock(PassiveSkill.class);
        PassiveSkill skill2 = mock(PassiveSkill.class);
        when(user.getPassiveSkills()).thenAnswer((Answer<Iterable<PassiveSkill>>)
                invocation -> Collections.singletonList(skill1));
        when(enemySide.get(0).getPassiveSkills()).thenAnswer((Answer<Iterable<PassiveSkill>>)
                invocation -> Collections.singletonList(skill2));

        battle.init();

        verify(user).setBattleClientAPI(battle);
        for (BattleParticipant bp : userSide) {
            verify(bp).setBattleClientAPI(battle);
        }
        for (BattleParticipant bp : enemySide) {
            verify(bp).setBattleClientAPI(battle);
        }
        verify(skill1).activate(user, enemySide.get(0));
        verify(skill2).activate(enemySide.get(0), user);
    }

    @Test
    public void testBattleFinishesWhenUserIsDead() {
        TestScheduler scheduler = new TestScheduler();
        disposable.add(battle.getFinishBattleObservable().observeOn(scheduler)
                .subscribe((data) -> summaryData = data));
        when(user.isAlive()).thenReturn(false);
        when(user.getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getOnDeathReward()).thenReturn(new BattleRewardData(1,2,3));

        battle.init();
        battle.start();
        battle.update(1);
        scheduler.triggerActions();

        assertEquals(1, summaryData.getRewardData().getManaReward());
        assertEquals(2, summaryData.getRewardData().getExperienceReward());
        assertEquals(3, summaryData.getRewardData().getCaseProbabilityReward());
    }

    @Test
    public void testUpdate() {
        when(user.isAlive()).thenReturn(true);
        when(enemySide.get(0).isAlive()).thenReturn(true);
        when(user.getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getPassiveSkills()).thenReturn(Collections.emptyList());
        battle.init();
        battle.start();

        battle.update(1);

        verify(user).update(1);
        for (BattleParticipant bp : userSide) {
            verify(bp).update(1);
        }
        for (BattleParticipant bp : enemySide) {
            verify(bp).update(1);
        }
    }

    @Test
    public void testRequireSkillApplication() {
        when(user.isAlive()).thenReturn(true);
        when(enemySide.get(0).isAlive()).thenReturn(true);
        ActiveSkill skill = mock(ActiveSkill.class);
        when(user.getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getPassiveSkills()).thenReturn(Collections.emptyList());
        battle.init();
        battle.start();
        battle.requestSkillApplication(user, skill, 1);
        battle.update(1.5f);
        verify(skill).activate(user, enemySide.get(0));
    }

    @AfterClass
    public static void clean() {
        disposable.dispose();
    }

}