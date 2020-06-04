package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SkillsOperationsTest {

    @Test
    public void mergeTable_AllToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void mergeTable_AllToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void mergeTable_AllToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void mergeTable_ActiveToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void mergeTable_ActiveToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void mergeTable_ActiveToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void mergeTable_PassiveToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void mergeTable_PassiveToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void mergeTable_PassiveToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }


    @Test
    public void mergeSkill_AllToAll_Success() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void mergeSkill_AllToAll_Failure_Name() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("b");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_AllToAll_Failure_Level() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(2);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_AllToAll_Failure_Null() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = null;
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_AllToActive_Success() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void mergeSkill_AllToActive_Failure_Name() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("b");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_AllToActive_Failure_Level() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(2);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_AllToActive_Failure_Empty() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = ActiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_AllToPassive_Success() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void mergeSkill_AllToPassive_Failure_Name() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("b");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_AllToPassive_Failure_Level() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(2);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_AllToPassive_Failure_Empty() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = PassiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_ActiveToAll_Success() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void mergeSkill_ActiveToAll_Failure_Name() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("b");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_ActiveToAll_Failure_Level() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(2);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_ActiveToAll_Failure_Null() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = null;
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_ActiveToActive_Success() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void mergeSkill_ActiveToActive_Failure_Name() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("b");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_ActiveToActive_Failure_Level() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(2);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_ActiveToActive_Failure_Empty() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = ActiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_PassiveToAll_Success() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void mergeSkill_PassiveToAll_Failure_Name() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("b");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_PassiveToAll_Failure_Level() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(2);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_PassiveToAll_Failure_Null() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = null;
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_PassiveToPassive_Success() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void mergeSkill_PassiveToPassive_Failure_Name() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("b");
        when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_PassiveToPassive_Failure_Level() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a");
        when(to.getLevel()).thenReturn(2);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void mergeSkill_PassiveToPassive_Failure_Empty() {
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = PassiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }


    @Test
    public void swapTable_AllToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void swapTable_AllToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void swapTable_AllToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void swapTable_ActiveToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void swapTable_ActiveToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void swapTable_ActiveToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void swapTable_PassiveToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void swapTable_PassiveToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void swapTable_PassiveToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }


    @Test
    public void swapSkill_AllToActive_Success() {
        Skill from = mock(ActiveSkill.class);
        Skill to = mock(ActiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void swapSkill_AllToActive_Failure_Subclass() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(ActiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_AllToActive_Failure_Empty() {
        Skill from = mock(ActiveSkill.class);
        when(from.getName()).thenReturn("a");
        when(from.getLevel()).thenReturn(1);
        Skill to = ActiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_AllToPassive_Success() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(PassiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void swapSkill_AllToPassive_Failure_Subclass() {
        Skill from = mock(ActiveSkill.class);
        Skill to = mock(PassiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_AllToPassive_Failure_Empty() {
        Skill from = mock(PassiveSkill.class);
        Skill to = PassiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_ActiveToAll_Success() {
        Skill from = mock(ActiveSkill.class);
        Skill to = mock(ActiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void swapSkill_ActiveToAll_Failure_Subclass() {
        Skill from = mock(ActiveSkill.class);
        Skill to = mock(PassiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_ActiveToAll_Failure_Null() {
        Skill from = mock(ActiveSkill.class);
        Skill to = null;
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_ActiveToActive_Success() {
        Skill from = mock(ActiveSkill.class);
        Skill to = mock(ActiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void swapSkill_ActiveToActive_Failure_Empty() {
        Skill from = mock(ActiveSkill.class);
        Skill to = ActiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_PassiveToAll_Success() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(PassiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void swapSkill_PassiveToAll_Failure_Subclass() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(ActiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_PassiveToAll_Failure_Null() {
        Skill from = mock(PassiveSkill.class);
        Skill to = null;
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void swapSkill_PassiveToPassive_Success() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(PassiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void swapSkill_PassiveToPassive_Failure_Empty() {
        Skill from = mock(PassiveSkill.class);
        Skill to = PassiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }


    @Test
    public void moveTable_AllToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void moveTable_AllToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void moveTable_AllToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void moveTable_ActiveToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void moveTable_ActiveToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void moveTable_ActiveToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void moveTable_PassiveToAll() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void moveTable_PassiveToActive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void moveTable_PassiveToPassive() {
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }


    @Test
    public void moveSkill_AllToActive_Success() {
        Skill from = mock(ActiveSkill.class);
        Skill to = ActiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void moveSkill_AllToActive_Failure_Subclass() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(Skill.class);
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void moveSkill_AllToActive_Failure_Second() {
        Skill from = mock(ActiveSkill.class);
        Skill to = mock(ActiveSkill.class);
        when(to.getRarity()).thenReturn(Rarity.COMMON);
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void moveSkill_AllToPassive_Success() {
        Skill from = mock(PassiveSkill.class);
        Skill to = PassiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void moveSkill_AllToPassive_Failure_Subclass() {
        Skill from = mock(ActiveSkill.class);
        Skill to = PassiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void moveSkill_AllToPassive_Failure_Second() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(PassiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void moveSkill_ActiveToAll_Success() {
        Skill from = mock(ActiveSkill.class);
        Skill to = null;
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void moveSkill_ActiveToAll_Failure_Second() {
        Skill from = mock(ActiveSkill.class);
        Skill to = mock(Skill.class);
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void moveSkill_ActiveToActive_Success() {
        Skill from = mock(ActiveSkill.class);
        Skill to = ActiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void moveSkill_ActiveToActive_Failure_Second() {
        Skill from = mock(ActiveSkill.class);
        Skill to = mock(ActiveSkill.class);
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void moveSkill_PassiveToAll_Success() {
        Skill from = mock(PassiveSkill.class);
        Skill to = null;
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void moveSkill_PassiveToAll_Failure_Second() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(Skill.class);
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }

    @Test
    public void moveSkill_PassiveToPassive_Success() {
        Skill from = mock(PassiveSkill.class);
        Skill to = PassiveSkill.getEmpty();
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);
    }

    @Test
    public void moveSkill_PassiveToPassive_Failure_Second() {
        Skill from = mock(PassiveSkill.class);
        Skill to = mock(PassiveSkill.class);
        when(to.getRarity()).thenReturn(Rarity.COMMON);
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.PASSIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }


}