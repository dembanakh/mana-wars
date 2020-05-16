package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.presentation.view.SkillsView;

import java.util.List;

public class SkillsPresenter {

    private SkillsView view;
    private SkillsInteractor interactor;

    public SkillsPresenter(SkillsView view, SkillsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void refreshSkillsList() {
        List<Skill> skills = interactor.getSkillsList();
        view.setSkillsList(skills);
    }

}
