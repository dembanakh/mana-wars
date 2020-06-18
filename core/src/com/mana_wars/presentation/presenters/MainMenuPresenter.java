package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.MainMenuView;

import io.reactivex.functions.Consumer;

public final class MainMenuPresenter extends BasePresenter<MainMenuView, MainMenuInteractor> {

    public MainMenuPresenter(MainMenuView view, MainMenuInteractor interactor, UIThreadHandler uiThreadHandler) {
        super(view, interactor, uiThreadHandler);
    }

    public void addObserver_manaAmount(Consumer<? super Integer> observer) {
        disposable.add(interactor.getManaAmountObservable().subscribe(observer));
    }

    public void addObserver_userLevel(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserLevelObservable().subscribe(observer));
    }

    public void onScreenShow() {
        disposable.add(interactor.initManaBonus().subscribe(time -> {
            uiThreadHandler.postRunnable(() -> {
                view.setTimeSinceLastManaBonusClaimed(time);
                if (interactor.isBonusAvailable()) view.onManaBonusReady();
            });
        }, Throwable::printStackTrace));

        disposable.add(interactor.getUsername().subscribe(username -> {
            uiThreadHandler.postRunnable(() -> {
                view.setUsername(username);
            });
        }, Throwable::printStackTrace));
    }

    public void onOpenSkillCase() {
        disposable.add(interactor.getNewSkill().subscribe(s -> {
            uiThreadHandler.postRunnable(() -> view.openSkillCaseWindow(s));
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
}
