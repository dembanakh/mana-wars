package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseBattleTest {

    private BaseBattle battle;

    private BattleParticipant user;
    private List<BattleParticipant> userSide;
    private List<BattleParticipant> enemySide;

    private BattleSummaryData summaryData;

    private static CompositeDisposable disposable = new CompositeDisposable();

    @Before
    public void setup() {
        user = mock(BattleParticipant.class);
        userSide = Collections.emptyList();
        enemySide = Collections.singletonList(mock(BattleParticipant.class));
        battle = new BaseBattle(user, userSide, enemySide);
    }

    @Test
    public void testInit() {
        PassiveSkill skill1 = mock(PassiveSkill.class);
        PassiveSkill skill2 = mock(PassiveSkill.class);
        when(user.getPassiveSkills()).thenReturn(Collections.singletonList(skill1));
        when(enemySide.get(0).getPassiveSkills()).thenReturn(Collections.singletonList(skill2));
        battle.init();

        verify(user).setBattle(battle);
        verify(enemySide.get(0)).setBattle(battle);
        verify(skill1).activate(user, enemySide.get(0));
        verify(skill2).activate(enemySide.get(0), user);
    }

    @Test
    public void testStart() {
        battle.start();

        verify(user).start();
        verify(enemySide.get(0)).start();
    }

    @Test
    public void testBattleFinishesWhenUserIsDead() {
        TestScheduler scheduler = new TestScheduler();
        disposable.add(battle.getFinishBattleObservable().observeOn(scheduler)
                .subscribe((data) -> summaryData = data));
        when(user.isAlive()).thenReturn(false);
        battle.init();
        battle.start();
        battle.update(1);
        scheduler.triggerActions();
        assertNotNull(summaryData);
    }

    @Test
    public void testRequireSkillApplication() {
        when(user.isAlive()).thenReturn(true);
        when(enemySide.get(0).isAlive()).thenReturn(true);
        ActiveSkill skill = mock(ActiveSkill.class);
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