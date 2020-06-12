package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.GreetingInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.GreetingView;

public class GreetingPresenter extends BasePresenter<GreetingView, GreetingInteractor>{

    public GreetingPresenter(GreetingView view, GreetingInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void onStart(String username) {
        interactor.registerUser(username);
        view.onStart();
    }

    public boolean isFirstTimeAppOpen() {
        return !interactor.hasUsername();
    }
}
