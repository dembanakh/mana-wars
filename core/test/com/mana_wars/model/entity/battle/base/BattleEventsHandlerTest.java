package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BattleEventsHandlerTest {

    private BattleEventsHandler eventsHandler;

    @Mock
    private BattleParticipant source;

    @Mock
    private BattleParticipant target;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        eventsHandler = new BattleEventsHandler();
    }

    @Test
    public void testAddedEventIsHandledInTime_AliveSource() {
        ActiveSkill skill = mock(ActiveSkill.class);
        when(source.isAlive()).thenReturn(true);

        eventsHandler.add(1.0, skill, source, target);

        eventsHandler.update(0.5);
        verifyNoMoreInteractions(skill, source, target);

        eventsHandler.update(1.0);
        verify(skill).activate(source, target);

        eventsHandler.update(1.5);
        verifyNoMoreInteractions(skill);
    }

    @Test
    public void testAddedEventIsHandledInTime_DeadSource() {
        ActiveSkill skill = mock(ActiveSkill.class);
        when(source.isAlive()).thenReturn(false);

        eventsHandler.add(1.0, skill, source, target);

        eventsHandler.update(0.5);
        verifyNoMoreInteractions(skill, source, target);

        eventsHandler.update(1.0);
        verifyNoMoreInteractions(skill);

        eventsHandler.update(1.5);
        verifyNoMoreInteractions(skill);
    }

    @Test
    public void test2EventsDifferentActivationTimesAreHandledInTime() {
        ActiveSkill skill1 = mock(ActiveSkill.class);
        ActiveSkill skill2 = mock(ActiveSkill.class);
        when(source.isAlive()).thenReturn(true);

        eventsHandler.add(1.0, skill1, source, target);
        eventsHandler.add(2.0, skill2, source, target);

        eventsHandler.update(0.5);
        verifyNoMoreInteractions(skill1, skill2);

        eventsHandler.update(1.0);
        verify(skill1).activate(source, target);
        verifyNoMoreInteractions(skill2);

        eventsHandler.update(1.5);
        verifyNoMoreInteractions(skill1, skill2);

        eventsHandler.update(2.0);
        verify(skill2).activate(source, target);
        verifyNoMoreInteractions(skill1);
    }

    @Test
    public void test2EventsSameActivationTimesAreHandledInTime() {
        ActiveSkill skill1 = mock(ActiveSkill.class);
        ActiveSkill skill2 = mock(ActiveSkill.class);
        when(source.isAlive()).thenReturn(true);

        eventsHandler.add(1.0, skill1, source, target);
        eventsHandler.add(1.0, skill2, source, target);

        eventsHandler.update(0.5);
        verifyNoMoreInteractions(skill1, skill2);

        eventsHandler.update(1.0);
        verify(skill1).activate(source, target);
        verify(skill2).activate(source, target);
    }

}