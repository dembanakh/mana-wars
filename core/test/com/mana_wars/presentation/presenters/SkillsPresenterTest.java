package com.mana_wars.presentation.presenters;

import com.mana_wars.model.SkillsListTriple;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.model.skills_operations.SkillTable;
import com.mana_wars.model.skills_operations.SkillsOperations;
import com.mana_wars.presentation.view.SkillsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class SkillsPresenterTest {

    private SkillsPresenter presenter;

    @Mock
    private SkillsView view;
    @Mock
    private SkillsInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new SkillsPresenter(view, interactor, Runnable::run);
    }

    @Test
    public void testRefreshSkillsList() {
        List<ActiveSkill> activeSkills = Collections.singletonList(mock(ActiveSkill.class));
        List<PassiveSkill> passiveSkills = Collections.singletonList(mock(PassiveSkill.class));
        List<Skill> allSkills = Collections.singletonList(mock(Skill.class));
        SkillsListTriple triple = new SkillsListTriple();
        triple.activeSkills.add(activeSkills.get(0));
        triple.passiveSkills.add(passiveSkills.get(0));
        triple.allSkills.add(allSkills.get(0));
        when(interactor.getUserSkills()).thenReturn(Single.just(triple));

        presenter.refreshSkillsList();

        verify(view).setSkillsList(activeSkills, passiveSkills, allSkills);
    }

    @Test
    public void testPerformOperation_Merge() {
        Skill source = mock(Skill.class);
        Skill target = mock(Skill.class);
        when(interactor.mergeSkills(target, source)).thenReturn(Completable.complete());
        when(interactor.validateOperation(SkillsOperations.MERGE,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(true);
        when(interactor.validateOperation(SkillsOperations.SWAP,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(true);
        when(interactor.validateOperation(SkillsOperations.MOVE,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(true);

        presenter.performOperation(SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target,
                0, 0);

        verify(target).upgradeLevel();
        verify(view).finishMerge(SkillTable.ALL_SKILLS, 0, target);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformOperation_Swap() {
        Skill source = mock(Skill.class);
        Skill target = mock(Skill.class);
        when(interactor.swapSkills(source, target)).thenReturn(Completable.complete());
        when(interactor.validateOperation(SkillsOperations.MERGE,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(false);
        when(interactor.validateOperation(SkillsOperations.SWAP,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(true);
        when(interactor.validateOperation(SkillsOperations.MOVE,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(true);

        presenter.performOperation(SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target,
                0, 0);

        verify(view).finishSwap(SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS,
                0, 0, source, target);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformOperation_Move() {
        Skill source = mock(Skill.class);
        Skill target = mock(Skill.class);
        when(interactor.moveSkill(source, -1)).thenReturn(Completable.complete());
        when(interactor.validateOperation(SkillsOperations.MERGE,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(false);
        when(interactor.validateOperation(SkillsOperations.SWAP,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(false);
        when(interactor.validateOperation(SkillsOperations.MOVE,
                SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target)).thenReturn(true);

        presenter.performOperation(SkillTable.ALL_SKILLS, SkillTable.ALL_SKILLS, source, target,
                0, 0);

        verify(view).finishMove(SkillTable.ALL_SKILLS, 0, source);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testOnSkillDragStart() {
        Skill source = mock(Skill.class);
        EnumMap<SkillTable, Iterable<? extends Skill>> tables = new EnumMap<>(SkillTable.class);
        Skill skill = mock(Skill.class);
        tables.put(SkillTable.ALL_SKILLS, Arrays.asList(source, skill));
        ActiveSkill activeSkill1 = mock(ActiveSkill.class);
        ActiveSkill activeSkill2 = mock(ActiveSkill.class);
        tables.put(SkillTable.ACTIVE_SKILLS, Arrays.asList(activeSkill1, activeSkill2));
        PassiveSkill passiveSkill1 = mock(PassiveSkill.class);
        PassiveSkill passiveSkill2 = mock(PassiveSkill.class);
        tables.put(SkillTable.PASSIVE_SKILLS, Arrays.asList(passiveSkill1, passiveSkill2));
        when(interactor.validateOperation(SkillsOperations.MERGE, SkillTable.ALL_SKILLS,
                SkillTable.ALL_SKILLS, source, source)).thenReturn(false);
        when(interactor.validateOperation(SkillsOperations.MERGE, SkillTable.ALL_SKILLS,
                SkillTable.ALL_SKILLS, source, skill)).thenReturn(true);
        when(interactor.validateOperation(SkillsOperations.MERGE, SkillTable.ALL_SKILLS,
                SkillTable.ACTIVE_SKILLS, source, activeSkill1)).thenReturn(true);
        when(interactor.validateOperation(SkillsOperations.MERGE, SkillTable.ALL_SKILLS,
                SkillTable.ACTIVE_SKILLS, source, activeSkill2)).thenReturn(false);
        when(interactor.validateOperation(SkillsOperations.MERGE, SkillTable.ALL_SKILLS,
                SkillTable.PASSIVE_SKILLS, source, passiveSkill1)).thenReturn(false);
        when(interactor.validateOperation(SkillsOperations.MERGE, SkillTable.ALL_SKILLS,
                SkillTable.PASSIVE_SKILLS, source, passiveSkill2)).thenReturn(true);

        presenter.onSkillDragStart(source, SkillTable.ALL_SKILLS, tables);

        verify(view).selectSkills(SkillTable.ALL_SKILLS, Collections.singletonList(1));
        verify(view).selectSkills(SkillTable.ACTIVE_SKILLS, Collections.singletonList(0));
        verify(view).selectSkills(SkillTable.PASSIVE_SKILLS, Collections.singletonList(1));
    }

    @Test
    public void testOnSkillDragStop() {
        presenter.onSkillDragStop();
        verify(view).clearSelection();
    }

}