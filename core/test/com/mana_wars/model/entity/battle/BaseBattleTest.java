package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.battle.base.BaseBattle;
import com.mana_wars.model.entity.battle.data.BattleRewardData;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseBattleTest {

    private BaseBattle battle;

    private com.mana_wars.model.entity.battle.participant.BattleParticipant user;
    private List<com.mana_wars.model.entity.battle.participant.BattleParticipant> userSide;
    private List<com.mana_wars.model.entity.battle.participant.BattleParticipant> enemySide;

    private BattleSummaryData summaryData;

    private static CompositeDisposable disposable = new CompositeDisposable();

    @Before
    public void setup() {
        user = mock(com.mana_wars.model.entity.battle.participant.BattleParticipant.class);
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

        verify(user).setBattleClientAPI(battle);
        verify(enemySide.get(0)).setBattleClientAPI(battle);
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
        when(user.getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getPassiveSkills()).thenReturn(Collections.emptyList());
        when(enemySide.get(0).getOnDeathReward()).thenReturn(new BattleRewardData(0,0,0));
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