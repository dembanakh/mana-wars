package com.mana_wars.model.entity.skills;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class BattleSkillTest {

    private ActiveSkill activeSkill;
    private BattleSkill skill;

    @Before
    public void setup() {
        activeSkill = mock(ActiveSkill.class);
        skill = new BattleSkill(activeSkill);
    }

    @Test
    public void testIsAvailableAt_Immediately() {
        assertTrue(skill.isAvailableAt(5));
    }

    @Test
    public void testIsAvailableAt_AfterSomeTime() {
        skill.updateAvailabilityTime(10);
        assertFalse(skill.isAvailableAt(5));
    }

    @Test
    public void testIsAvailableAt_Max() {
        skill.updateAvailabilityTime(10);
        skill.updateAvailabilityTime(8);
        assertFalse(skill.isAvailableAt(9));
    }

}