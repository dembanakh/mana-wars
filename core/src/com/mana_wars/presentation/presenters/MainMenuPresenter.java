package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.view.MainMenuView;

//todo add implements MainMenuPresenterCallback
public class MainMenuPresenter {

    private MainMenuView view;
    private MainMenuInteractor interactor;

    public MainMenuPresenter(MainMenuView view, MainMenuInteractor interactor){
        this.view = view;
        this.interactor=interactor;
    }

    public void onOpenSkillCase() {
        Skill s = interactor.getNewSkill();
        view.showNewSkillWindow(s.getIconID(), s.getName(), s.getDescription());
    }
}
