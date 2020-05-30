package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.GreetingInteractor;
import com.mana_wars.presentation.view.GreetingView;

public class GreetingPresenter {

    private GreetingView view;
    private GreetingInteractor interactor;

    public GreetingPresenter(GreetingView view, GreetingInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void onStart(String username) {
        interactor.registerUser(username);
        view.onStart();
    }

    public void dispose(){
        interactor.dispose();
    }

    public boolean isFirstTimeAppOpen() {
        return !interactor.hasUsername();
    }
}
