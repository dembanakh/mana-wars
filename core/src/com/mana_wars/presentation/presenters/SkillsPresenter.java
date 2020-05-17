package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.presentation.view.SkillsView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SkillsPresenter {

    private SkillsView view;
    private SkillsInteractor interactor;

    private CompositeDisposable disposable = new CompositeDisposable();

    public SkillsPresenter(SkillsView view, SkillsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void refreshSkillsList() {
        disposable.add(interactor.getUserSkills().subscribe(skills -> {
            Gdx.app.postRunnable( ()-> view.setSkillsList(skills));
        }, Throwable::printStackTrace));
    }

    public void dispose(){
        disposable.dispose();
    }
}
