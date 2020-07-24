package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.MainMenuView;

import io.reactivex.functions.Consumer;

public final class MainMenuPresenter extends BasePresenter<MainMenuView, MainMenuInteractor> {

    public MainMenuPresenter(MainMenuView view, MainMenuInteractor interactor, UIThreadHandler uiThreadHandler) {
        super(view, interactor, uiThreadHandler);
    }

    public void onScreenShow() {
        disposable.add(interactor.initManaBonus().subscribe(time ->
            uiThreadHandler.postRunnable(() -> synchronizeManaBonusTime(time)),
                Throwable::printStackTrace));

        disposable.add(interactor.getUsername().subscribe(username ->
            uiThreadHandler.postRunnable(() -> view.setUsername(username)),
                Throwable::printStackTrace));
    }

    public void onOpenSkillCase() {
        if (interactor.getUserSkillCasesNumber() > 0) {
            disposable.add(interactor.getNewSkill().subscribe(s -> {
                final int actualCasesNumber = interactor.useSkillCase();
                uiThreadHandler.postRunnable(() -> {
                    view.openSkillCaseWindow(s);
                    view.setSkillCasesNumber(actualCasesNumber);
                });
            }, Throwable::printStackTrace));
        }
    }

    public void synchronizeManaBonusTime() {
        synchronizeManaBonusTime(interactor.getTimeSinceLastManaBonusClaim());
    }

    public void refreshSkillCasesNumber() {
        view.setSkillCasesNumber(interactor.getUserSkillCasesNumber());
    }

    private void synchronizeManaBonusTime(long time) {
        view.setTimeSinceLastManaBonusClaimed(time);
        if (interactor.isBonusAvailable()) view.onManaBonusReady();
    }

    public void addObserver_manaAmount(Consumer<? super Integer> observer) {
        disposable.add(interactor.getManaAmountObservable().subscribe(observer));
    }

    public void addObserver_userLevel(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserLevelObservable().subscribe(observer));
    }

    public void addObserver_userExperience(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserExperienceObservable().subscribe(observer));
    }

    public void addObserver_userNextLevelRequiredExperienceObserver(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserNextLevelRequiredExperienceObservable().subscribe(observer));
    }

    public int getFullManaBonusTimeout() {
        return interactor.getFullManaBonusTimeout();
    }

    public void claimBonus() {
        interactor.claimBonus();
    }
}
