package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInfoInteractor;
import com.mana_wars.presentation.view.SkillsInfoView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SkillsInfoPresenterTest {

    private SkillsInfoPresenter presenter;

    @Mock
    private SkillsInfoView view;
    @Mock
    private SkillsInfoInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new SkillsInfoPresenter(view, interactor, Runnable::run);
    }

    @Test
    public void testFetchSkillsList() {
        List<Skill> skills = Collections.singletonList(mock(Skill.class));
        when(interactor.getAllSkills()).thenAnswer((Answer<Single<List<Skill>>>)
                invocation -> Single.just(skills));

        presenter.fetchSkillsList();

        verify(view).setSkillsList(skills);
    }

}