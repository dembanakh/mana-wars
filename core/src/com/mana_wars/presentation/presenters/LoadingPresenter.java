package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.LoadingInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.LoadingView;

public class LoadingPresenter extends BasePresenter<LoadingView, LoadingInteractor> {

    public LoadingPresenter(LoadingView view, LoadingInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void checkApplicationUpdate() {
        disposable.add(
                interactor.checkApplicationUpdate().subscribe(() -> {
                    uiThreadHandler.postRunnable(view::goToGreetingScreen);
                }, error -> {
                    error.printStackTrace();
                    uiThreadHandler.postRunnable(view::exitFromApplication);
                })
        );
    }
}
