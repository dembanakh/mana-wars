package com.mana_wars.model;

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
        SkillsOperations.TableOperationQuery toq = SkillsOperations.can(SkillsOperations.MERGE);
        SkillsOperations.SkillOperationQuery soq = toq.from(SkillsOperations.Table.ACTIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        assertTrue(soq.isValid());
        soq = toq.from(SkillsOperations.Table.PASSIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        assertFalse(soq.isValid());
        soq = toq.from(SkillsOperations.Table.ALL_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        assertTrue(soq.isValid());

    }

    @Test
    public void MergeSkillTest(){
        SkillsOperations.TableOperationQuery tableQuery = SkillsOperations.can(SkillsOperations.MERGE);
        SkillsOperations.SkillOperationQuery soq = tableQuery.from(SkillsOperations.Table.ACTIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        Skill from = mock(Skill.class);
        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        Skill to = mock(Skill.class);
        when(to.getName()).thenReturn("a"); when(to.getLevel()).thenReturn(1);
        boolean result = soq.from(from).to(to);
        assertTrue(result);

        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        when(to.getName()).thenReturn("b"); when(to.getLevel()).thenReturn(1);
        result = soq.from(from).to(to);
        assertFalse(result);

        when(from.getName()).thenReturn("a"); when(from.getLevel()).thenReturn(1);
        when(to.getName()).thenReturn("a"); when(to.getLevel()).thenReturn(2);
        result = soq.from(from).to(to);
        assertFalse(result);

    }


    @Test
    public void SwapTableTest(){
        SkillsOperations.TableOperationQuery toq = SkillsOperations.can(SkillsOperations.SWAP);
        SkillsOperations.SkillOperationQuery soq = toq.from(SkillsOperations.Table.ACTIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        assertTrue(soq.isValid());
        soq = toq.from(SkillsOperations.Table.PASSIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        assertFalse(soq.isValid());
        soq = toq.from(SkillsOperations.Table.ALL_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        assertTrue(soq.isValid());
        soq = toq.from(SkillsOperations.Table.ALL_SKILLS).to(SkillsOperations.Table.ALL_SKILLS);
        assertFalse(soq.isValid());

    }

    @Test
    public void SwapSkillTest(){
        SkillsOperations.TableOperationQuery tableQuery = SkillsOperations.can(SkillsOperations.SWAP);
        SkillsOperations.SkillOperationQuery soq = tableQuery.from(SkillsOperations.Table.ACTIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        Skill from = mock(Skill.class);
        boolean result = soq.from(from).to(Skill.Empty);
        assertFalse(result);

        Skill to = mock(Skill.class);
        result = soq.from(from).to(to);
        assertTrue(result);

        soq = tableQuery.from(SkillsOperations.Table.ALL_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);

        from = mock(ActiveSkill.class);
        to = mock(PassiveSkill.class);
        result = soq.from(from).to(to);
        assertFalse(result);

        to = mock(ActiveSkill.class);
        result = soq.from(from).to(to);
        assertTrue(result);

    }

/*MOVE {
        @Override
        protected boolean validate(SkillOperationQuery query) {
            if (query.tableQuery.source == query.tableQuery.target) return true;
            if (query.tableQuery.source == Table.ALL_SKILLS) {
                if (query.tableQuery.target == Table.ACTIVE_SKILLS)
                    return (query.source instanceof ActiveSkill) && (query.target == Skill.Empty);
                if (query.tableQuery.target == Table.PASSIVE_SKILLS)
                    return (query.source instanceof PassiveSkill) && (query.target == Skill.Empty);
            }
            return query.target == null;
        }

        }*/


    @Test
    public void MoveTableTest(){
        SkillsOperations.TableOperationQuery toq = SkillsOperations.can(SkillsOperations.MOVE);
        SkillsOperations.SkillOperationQuery soq = toq.from(SkillsOperations.Table.ACTIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        assertTrue(soq.isValid());
        soq = toq.from(SkillsOperations.Table.PASSIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        assertFalse(soq.isValid());
        soq = toq.from(SkillsOperations.Table.ALL_SKILLS).to(SkillsOperations.Table.ALL_SKILLS);
        assertFalse(soq.isValid());

    }

    @Test
    public void MoveSkillTest(){
        SkillsOperations.TableOperationQuery tableQuery = SkillsOperations.can(SkillsOperations.MOVE);
        SkillsOperations.SkillOperationQuery soq = tableQuery.from(SkillsOperations.Table.ACTIVE_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);
        Skill from = mock(Skill.class);
        boolean result = soq.from(from).to(Skill.Empty);
        assertTrue(result);

        Skill to = mock(Skill.class);
        result = soq.from(from).to(to);
        assertTrue(result);

        soq = tableQuery.from(SkillsOperations.Table.ALL_SKILLS).to(SkillsOperations.Table.ACTIVE_SKILLS);

        from = mock(ActiveSkill.class);
        to = mock(PassiveSkill.class);
        result = soq.from(from).to(to);
        assertFalse(result);

        result = soq.from(from).to(Skill.Empty);
        assertTrue(result);

        from = mock(PassiveSkill.class);
        result = soq.from(from).to(to);
        assertFalse(result);


    }



}