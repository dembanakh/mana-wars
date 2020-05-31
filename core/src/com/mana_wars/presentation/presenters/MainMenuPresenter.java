package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.model.mana_bonus.ManaBonus;
import com.mana_wars.presentation.view.MainMenuView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainMenuPresenter {

    private final MainMenuView view;
    private final MainMenuInteractor interactor;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public MainMenuPresenter(final MainMenuView view, final MainMenuInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void init() {
        interactor.init();
    }

    public void initCallbacks(final Consumer<? super Integer> manaAmountCallback,
                              final Consumer<? super Integer> userLevelCallback) {
        disposable.add(interactor.getManaAmountObservable().subscribe(manaAmountCallback, Throwable::printStackTrace));
        disposable.add(interactor.getUserLevelObservable().subscribe(userLevelCallback, Throwable::printStackTrace));
    }

    public void onOpenSkillCase() {
        disposable.add(interactor.getNewSkill().subscribe(s -> {
                Gdx.app.postRunnable( ()->view.openSkillCaseWindow(s.getIconID(),
                        s.getName(), s.getRarity(), s.getDescription()));
        }, Throwable::printStackTrace));
    }

    public int getFullManaBonusTimeout() {
        return interactor.getFullManaBonusTimeout();
    }

    public void claimBonus() {
        interactor.claimBonus();
    }

    public void synchronizeManaBonusTime() {
        view.setTimeSinceLastManaBonusClaimed(interactor.getTimeSinceLastManaBonusClaim());
        if (interactor.isBonusAvailable()) view.onManaBonusReady();
    }

    public void dispose(){
        disposable.dispose();
        interactor.dispose();
    }

}
