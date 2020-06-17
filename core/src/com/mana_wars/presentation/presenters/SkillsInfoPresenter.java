package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.SkillsInfoInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.SkillsInfoView;

public final class SkillsInfoPresenter extends BasePresenter<SkillsInfoView, SkillsInfoInteractor> {

    public SkillsInfoPresenter(SkillsInfoView view, SkillsInfoInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void fetchSkillsList() {
        disposable.add(interactor.getAllSkills().subscribe(skills -> {
            uiThreadHandler.postRunnable(() -> view.setSkillsList(skills));
        }, Throwable::printStackTrace));
    }
    
}
