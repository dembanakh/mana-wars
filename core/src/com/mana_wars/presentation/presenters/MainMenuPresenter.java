package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.MainMenuInteractor;

import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.MainMenuView;
import io.reactivex.disposables.CompositeDisposable;

public class MainMenuPresenter {

    private final MainMenuView view;
    private final MainMenuInteractor interactor;
    private UIThreadHandler uiThreadHandler;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public MainMenuPresenter(MainMenuView view, UIThreadHandler uiThreadHandler, MainMenuInteractor interactor){
        this.view = view;
        this.uiThreadHandler = uiThreadHandler;
        this.interactor = interactor;
    }

    public void init() {
        view.initObservers(disposable, interactor.getManaAmountObservable(),
                interactor.getUserLevelObservable(), interactor.getUsernameObservable());
    }

    public void onScreenShow() {
        interactor.initManaBonus();
    }

    public void onOpenSkillCase() {
        System.out.println("ON OPEN");
        disposable.add(interactor.getNewSkill().subscribe(s -> {
            uiThreadHandler.postRunnable( ()->view.openSkillCaseWindow(s.getIconID(),
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
