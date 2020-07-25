package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.user.UserSkillsAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SkillsInteractorTest {

    private SkillsInteractor interactor;

    @Mock
    private UserSkillsAPI user;
    @Mock
    private DatabaseRepository databaseRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new SkillsInteractor(user, databaseRepository);
    }

    @Test
    public void testMergeSkills() {
        Skill toUpdate = mock(Skill.class);
        Skill toDelete = mock(Skill.class);

        interactor.mergeSkills(toUpdate, toDelete);

        verify(databaseRepository).mergeSkills(toUpdate, toDelete);
    }

    @Test
    public void testMoveSkill() {
        Skill toUpdate = mock(Skill.class);

        interactor.moveSkill(toUpdate, 10);

        verify(databaseRepository).moveSkill(toUpdate, 10);
    }

    @Test
    public void testSwapSkills() {
        Skill source = mock(Skill.class);
        Skill target = mock(Skill.class);

        interactor.swapSkills(source, target);

        verify(databaseRepository).swapSkills(source, target);
    }

}