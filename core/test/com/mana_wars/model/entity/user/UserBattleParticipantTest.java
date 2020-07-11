package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.participant.BattleParticipantBattleAPI;
import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UserBattleParticipantTest {

    private UserBattleParticipant user;

    private ActiveSkill activeSkill;
    private PassiveSkill passiveSkill;

    private BattleParticipantBattleAPI battleParticipantBattleAPI;

    private int changedMana;

    @Before
    public void setup() {
        activeSkill = mock(ActiveSkill.class);
        passiveSkill = mock(PassiveSkill.class);
        battleParticipantBattleAPI = mock(BattleParticipantBattleAPI.class);

        user = new UserBattleParticipant("a", 100, (mana) -> changedMana = mana,
                Collections.singletonList(activeSkill), Collections.singletonList(passiveSkill));
        user.setBattleParticipantBattleAPI(battleParticipantBattleAPI);
    }

    @Test
    public void testApplySkillCharacteristic_MANA() {
        SkillCharacteristic sc = new SkillCharacteristic(20, Characteristic.MANA,
                ValueChangeType.DECREASE, SkillCharacteristic.Target.SELF,
                UpgradeFunction.LINEAR, 0);
        user.applySkillCharacteristic(sc, 2);
        assertEquals(80, changedMana);
    }

    @Test
    public void testUpdateDoesNothingWhenNoSkillIsApplied() {
        user.update(50);
        verifyNoMoreInteractions(battleParticipantBattleAPI);
    }

    @Test
    public void testTryApplyActiveSkill_Immediate() {
        when(activeSkill.getManaCost()).thenReturn(20);
        assertTrue(user.tryApplyActiveSkill(0));
    }

    @Test
    public void testTryApplyActiveSkillWorks() {
        when(activeSkill.getManaCost()).thenReturn(20);
        when(activeSkill.getCastTime(100)).thenReturn(1d);
        when(activeSkill.getCooldown(100)).thenReturn(2d);
        user.tryApplyActiveSkill(0);
        user.update(0.5f);
        verify(battleParticipantBattleAPI).requestSkillApplication(user, activeSkill, 1);
    }

    @Test
    public void testTryApplyActiveSkillBlocksAllSkillsForSomeTime() {
        when(activeSkill.getManaCost()).thenReturn(20);
        when(activeSkill.getCastTime(100)).thenReturn(1d);
        when(activeSkill.getCooldown(100)).thenReturn(2d);
        user.tryApplyActiveSkill(0);
        user.update(0.5f);
        user.update(1);
        assertFalse(user.tryApplyActiveSkill(0));
    }

}