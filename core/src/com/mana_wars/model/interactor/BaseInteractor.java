package com.mana_wars.model.interactor;

import io.reactivex.disposables.CompositeDisposable;

public class BaseInteractor {

    protected final CompositeDisposable disposable = new CompositeDisposable();

    public void dispose() {
        disposable.dispose();
    }
}
