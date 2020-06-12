package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.BaseInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter <T extends BaseInteractor>{

    protected final UIThreadHandler uiThreadHandler;
    protected final T interactor;
    protected final CompositeDisposable disposable = new CompositeDisposable();

    public BasePresenter(T interactor, UIThreadHandler handler){
        this.interactor = interactor;
        this.uiThreadHandler = handler;
    }

    public void dispose() {
        disposable.dispose();
        interactor.dispose();
    }

}
