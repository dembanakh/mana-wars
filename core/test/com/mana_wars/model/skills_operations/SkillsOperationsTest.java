package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.SkillTable;
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
    public void MergeTableTest(){
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MERGE)
                                                .from(SkillTable.ACTIVE_SKILLS)
                                                .to(SkillTable.ACTIVE_SKILLS)
                                                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
        soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
        soq = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void MergeSkillTest(){
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a"); when(to.getLevel()).thenReturn(1);
        boolean result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);

        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        when(to.getName()).thenReturn("b"); when(to.getLevel()).thenReturn(1);
        result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);

        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        when(to.getName()).thenReturn("a"); when(to.getLevel()).thenReturn(2);
        result = SkillsOperations.can(SkillsOperations.MERGE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);
    }


    @Test
    public void SwapTableTest(){
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.SWAP)
                                                .from(SkillTable.ACTIVE_SKILLS)
                                                .to(SkillTable.ACTIVE_SKILLS)
                                                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
        soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.PASSIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
        soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
        soq = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ALL_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void SwapSkillTest(){
        Skill from = mock(Skill.class);
        boolean result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(Skill.Empty).validate();
        assertFalse(result);

        Skill to = mock(Skill.class);
        result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);

        from = mock(PassiveSkill.class);
        to = mock(ActiveSkill.class);
        result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertFalse(result);

        result = SkillsOperations.can(SkillsOperations.SWAP)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(Skill.Empty).validate();
        assertFalse(result);
    }

    @Test
    public void MoveTableTest(){
        OperationQuery<Skill, Boolean> soq = SkillsOperations.can(SkillsOperations.MOVE)
                                                .from(SkillTable.ACTIVE_SKILLS)
                                                .to(SkillTable.ACTIVE_SKILLS)
                                                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
        soq = SkillsOperations.can(SkillsOperations.MOVE)
                    .from(SkillTable.PASSIVE_SKILLS)
                    .to(SkillTable.ACTIVE_SKILLS)
                    .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
        soq = SkillsOperations.can(SkillsOperations.MOVE)
                    .from(SkillTable.ALL_SKILLS)
                    .to(SkillTable.ALL_SKILLS)
                    .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void MoveSkillTest() {
        Skill from = mock(Skill.class);
        boolean result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(Skill.Empty).validate();
        assertTrue(result);

        Skill to = mock(Skill.class);
        result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ACTIVE_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate()
                .from(from).to(to).validate();
        assertTrue(result);

        from = mock(PassiveSkill.class);
        to = mock(ActiveSkill.class);
        result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate().from(from).to(to).validate();
        assertFalse(result);

        result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate().from(from).to(Skill.Empty).validate();
        assertFalse(result);

        from = mock(PassiveSkill.class);
        result = SkillsOperations.can(SkillsOperations.MOVE)
                .from(SkillTable.ALL_SKILLS)
                .to(SkillTable.ACTIVE_SKILLS)
                .validate().from(from).to(to).validate();
        assertFalse(result);
    }



}