package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.participant.BattleClientAPI;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UserBattleParticipantTest {

    private UserBattleParticipant user;

    @Mock
    private ActiveSkill activeSkill;
    @Mock
    private PassiveSkill passiveSkill;

    @Mock
    private BattleClientAPI battleClientAPI;

    private int changedMana;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        user = new UserBattleParticipant("a", 1, 100, (mana) -> changedMana = mana,
                Collections.singletonList(activeSkill), Collections.singletonList(passiveSkill));
        user.setBattleClientAPI(battleClientAPI);
    }

    @Test
    public void testApplySkillCharacteristic_MANA() {
        SkillCharacteristic sc = new SkillCharacteristic(20, Characteristic.MANA,
                ValueChangeType.DECREASE, 0,
                UpgradeFunction.LINEAR, 0);
        user.applySkillCharacteristic(sc, 2);
        assertEquals(80, changedMana);
    }

    @Test
    public void testUpdateDoesNothingWhenNoSkillIsApplied() {
        user.update(50);
        verifyNoMoreInteractions(battleClientAPI);
    }

    @Test
    public void testTryApplyActiveSkill_Immediate() {
        when(activeSkill.getManaCost()).thenReturn(20);
        assertTrue(user.tryApplyActiveSkill(activeSkill));
    }

    @Test
    public void testTryApplyActiveSkillWorks() {
        when(activeSkill.getManaCost()).thenReturn(20);
        when(activeSkill.getCastTime(100)).thenReturn(1d);
        when(activeSkill.getCooldown(100)).thenReturn(2d);
        user.tryApplyActiveSkill(activeSkill);
        user.update(0.5f);
        verify(battleClientAPI).requestSkillApplication(user, activeSkill, 1);
    }

    @Test
    public void testTryApplyActiveSkillBlocksAllSkillsForSomeTime() {
        when(activeSkill.getManaCost()).thenReturn(20);
        when(activeSkill.getCastTime(100)).thenReturn(1d);
        when(activeSkill.getCooldown(100)).thenReturn(2d);
        user.tryApplyActiveSkill(activeSkill);
        user.update(0.5f);
        user.update(1);
        assertFalse(user.tryApplyActiveSkill(activeSkill));
    }

}