package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.view.MainMenuView;

import io.reactivex.disposables.CompositeDisposable;

//todo add implements MainMenuPresenterCallback
public class MainMenuPresenter {

    private MainMenuView view;
    private MainMenuInteractor interactor;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MainMenuPresenter(MainMenuView view, MainMenuInteractor interactor){
        this.view = view;
        this.interactor=interactor;
    }

    public void onOpenSkillCase() {
        disposable.add(interactor.getNewSkill().subscribe(s -> {
                Gdx.app.postRunnable( ()->view.openSkillCaseWindow(s.getIconID(), s.getName(), s.getDescription()));
        }, Throwable::printStackTrace));
    }

    public void dispose(){
        disposable.dispose();
        interactor.dispose();
    }
}
