package com.mana_wars.model.skills_operations;

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
        OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> toq =
                SkillsOperations.can(SkillsOperations.MERGE);
        OperationQuery<Skill, Boolean> soq = toq.from(SkillsOperations.Table.ACTIVE_SKILLS)
                                                .to(SkillsOperations.Table.ACTIVE_SKILLS)
                                                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
        soq = toq.from(SkillsOperations.Table.PASSIVE_SKILLS)
                .to(SkillsOperations.Table.ACTIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
        soq = toq.from(SkillsOperations.Table.ALL_SKILLS)
                .to(SkillsOperations.Table.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
    }

    @Test
    public void MergeSkillTest(){
        OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> tableQuery =
                SkillsOperations.can(SkillsOperations.MERGE);
        OperationQuery<Skill, Boolean> soq = tableQuery.from(SkillsOperations.Table.ACTIVE_SKILLS)
                                                        .to(SkillsOperations.Table.ACTIVE_SKILLS)
                                                        .validate();
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a"); when(to.getLevel()).thenReturn(1);
        boolean result = soq.from(from).to(to).validate();
        assertTrue(result);

        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        when(to.getName()).thenReturn("b"); when(to.getLevel()).thenReturn(1);
        result = soq.from(from).to(to).validate();
        assertFalse(result);

        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        when(to.getName()).thenReturn("a"); when(to.getLevel()).thenReturn(2);
        result = soq.from(from).to(to).validate();
        assertFalse(result);

    }


    @Test
    public void SwapTableTest(){
        OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> toq =
                SkillsOperations.can(SkillsOperations.SWAP);
        OperationQuery<Skill, Boolean> soq = toq.from(SkillsOperations.Table.ACTIVE_SKILLS)
                                                .to(SkillsOperations.Table.ACTIVE_SKILLS)
                                                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
        soq = toq.from(SkillsOperations.Table.PASSIVE_SKILLS)
                .to(SkillsOperations.Table.ACTIVE_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
        soq = toq.from(SkillsOperations.Table.ALL_SKILLS)
                .to(SkillsOperations.Table.ACTIVE_SKILLS)
                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
        soq = toq.from(SkillsOperations.Table.ALL_SKILLS)
                .to(SkillsOperations.Table.ALL_SKILLS)
                .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);

    }

    @Test
    public void SwapSkillTest(){
        OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> tableQuery =
                SkillsOperations.can(SkillsOperations.SWAP);
        OperationQuery<Skill, Boolean> soq = tableQuery.from(SkillsOperations.Table.ACTIVE_SKILLS)
                                                        .to(SkillsOperations.Table.ACTIVE_SKILLS)
                                                        .validate();
        Skill from = mock(Skill.class);
        boolean result = soq.from(from).to(Skill.Empty).validate();
        assertFalse(result);

        Skill to = mock(Skill.class);
        result = soq.from(from).to(to).validate();
        assertTrue(result);

        soq = tableQuery.from(SkillsOperations.Table.ALL_SKILLS)
                        .to(SkillsOperations.Table.ACTIVE_SKILLS)
                        .validate();

        from = mock(ActiveSkill.class);
        to = mock(PassiveSkill.class);
        result = soq.from(from).to(to).validate();
        assertFalse(result);

        to = mock(ActiveSkill.class);
        result = soq.from(from).to(to).validate();
        assertTrue(result);

    }

    @Test
    public void MoveTableTest(){
        OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> toq =
                SkillsOperations.can(SkillsOperations.MOVE);
        OperationQuery<Skill, Boolean> soq = toq.from(SkillsOperations.Table.ACTIVE_SKILLS)
                                                .to(SkillsOperations.Table.ACTIVE_SKILLS)
                                                .validate();
        assertFalse(soq instanceof EmptySkillOperationQuery);
        soq = toq.from(SkillsOperations.Table.PASSIVE_SKILLS)
                    .to(SkillsOperations.Table.ACTIVE_SKILLS)
                    .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);
        soq = toq.from(SkillsOperations.Table.ALL_SKILLS)
                    .to(SkillsOperations.Table.ALL_SKILLS)
                    .validate();
        assertTrue(soq instanceof EmptySkillOperationQuery);

    }

    @Test
    public void MoveSkillTest(){
        OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> tableQuery =
                SkillsOperations.can(SkillsOperations.MOVE);
        OperationQuery<Skill, Boolean> soq = tableQuery.from(SkillsOperations.Table.ACTIVE_SKILLS)
                                                                .to(SkillsOperations.Table.ACTIVE_SKILLS)
                                                                .validate();
        Skill from = mock(Skill.class);
        boolean result = soq.from(from).to(Skill.Empty).validate();
        assertTrue(result);

        Skill to = mock(Skill.class);
        result = soq.from(from).to(to).validate();
        assertTrue(result);

        soq = tableQuery.from(SkillsOperations.Table.ALL_SKILLS)
                        .to(SkillsOperations.Table.ACTIVE_SKILLS)
                        .validate();

        from = mock(ActiveSkill.class);
        to = mock(PassiveSkill.class);
        result = soq.from(from).to(to).validate();
        assertFalse(result);

        result = soq.from(from).to(Skill.Empty).validate();
        assertTrue(result);

        from = mock(PassiveSkill.class);
        result = soq.from(from).to(to).validate();
        assertFalse(result);


    }



}