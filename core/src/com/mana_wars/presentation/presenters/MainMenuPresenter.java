package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.view.MainMenuView;

import java.util.Random;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

//todo add implements MainMenuPresenterCallback
public class MainMenuPresenter {

    private final MainMenuView view;
    private final MainMenuInteractor interactor;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public MainMenuPresenter(MainMenuView view, MainMenuInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void initCallbacks(Consumer<? super Integer> manaAmountCallback,
                              Consumer<? super Integer> userLevelCallback) {
        disposable.add(interactor.getManaAmountObservable().subscribe(manaAmountCallback, Throwable::printStackTrace));
        disposable.add(interactor.getUserLevelObservable().subscribe(userLevelCallback, Throwable::printStackTrace));
    }

    public void test_resetFields() {
        interactor.test_updateLevel(new Random().nextInt(100));
        interactor.test_updateManaAmount(new Random().nextInt(100));
    }

    public void onOpenSkillCase() {
        disposable.add(interactor.getNewSkill().subscribe(s -> {
                Gdx.app.postRunnable( ()->view.openSkillCaseWindow(s.getIconID(),
                        s.getName(), s.getRarity(), s.getDescription()));
        }, Throwable::printStackTrace));
    }

    public void dispose(){
        disposable.dispose();
        interactor.dispose();
    }

}
