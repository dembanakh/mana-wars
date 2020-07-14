package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.ImmutableBattleSkill;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BaseSkillsSetTest {

    private BaseSkillsSet skillsSet;

    @Mock
    private ActiveSkill skill1;

    @Mock
    private ActiveSkill skill2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        skillsSet = new BaseSkillsSet(Arrays.asList(skill1, skill2));
    }

    @Test
    public void testAdd() {
        List<ImmutableBattleSkill> elements = skillsSetElements();
        assertEquals(2, elements.size());
        assertEquals(skill1, elements.get(0).getSkill());
        assertTrue(elements.get(0).isAvailableAt(1));
        assertEquals(skill2, elements.get(1).getSkill());
        assertTrue(elements.get(1).isAvailableAt(2));
    }

    @Test
    public void testOnSkillAppliedSingle() {
        skillsSet.onSkillApplied(skill1, 1, 0.5, 1);

        List<ImmutableBattleSkill> elements = skillsSetElements();
        assertEquals(2, elements.size());
        assertFalse(elements.get(0).isAvailableAt(1));
        assertFalse(elements.get(1).isAvailableAt(1));

        assertFalse(elements.get(0).isAvailableAt(1.5));
        assertTrue(elements.get(1).isAvailableAt(1.5));

        assertFalse(elements.get(0).isAvailableAt(2));
        assertTrue(elements.get(1).isAvailableAt(2));

        assertTrue(elements.get(0).isAvailableAt(2.5));
        assertTrue(elements.get(1).isAvailableAt(2.5));
    }

    @Test
    public void testOnSkillAppliedCollisionSimple() {
        skillsSet.onSkillApplied(skill1, 1, 0.5, 1);
        skillsSet.onSkillApplied(skill2, 1.5, 0.5, 1);

        List<ImmutableBattleSkill> elements = skillsSetElements();
        assertEquals(2, elements.size());

        assertFalse(elements.get(0).isAvailableAt(1.5));
        assertFalse(elements.get(1).isAvailableAt(1.5));

        assertFalse(elements.get(0).isAvailableAt(2));
        assertFalse(elements.get(1).isAvailableAt(2));

        assertTrue(elements.get(0).isAvailableAt(2.5));
        assertFalse(elements.get(1).isAvailableAt(2.5));

        assertTrue(elements.get(0).isAvailableAt(3));
        assertTrue(elements.get(1).isAvailableAt(3));
    }

    @Test
    public void testOnSkillAppliedCollision() {
        skillsSet.onSkillApplied(skill1, 1, 0.5, 1);
        skillsSet.onSkillApplied(skill2, 2, 1, 1);

        List<ImmutableBattleSkill> elements = skillsSetElements();
        assertEquals(2, elements.size());

        assertFalse(elements.get(0).isAvailableAt(2));
        assertFalse(elements.get(1).isAvailableAt(2));

        assertFalse(elements.get(0).isAvailableAt(2.5));
        assertFalse(elements.get(1).isAvailableAt(2.5));

        assertTrue(elements.get(0).isAvailableAt(3));
        assertFalse(elements.get(1).isAvailableAt(3));

        assertTrue(elements.get(0).isAvailableAt(4));
        assertTrue(elements.get(1).isAvailableAt(4));
    }

    private List<ImmutableBattleSkill> skillsSetElements() {
        List<ImmutableBattleSkill> elements = new ArrayList<>();
        for (ImmutableBattleSkill battleSkill : skillsSet.elements()) {
            elements.add(battleSkill);
        }
        return elements;
    }

}