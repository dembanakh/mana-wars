package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.user.UserBaseAPI;

import io.reactivex.disposables.CompositeDisposable;

public class BaseInteractor <T extends UserBaseAPI> {

    protected final T user;
    protected final CompositeDisposable disposable = new CompositeDisposable();

    protected BaseInteractor(T user) {
        this.user = user;
    }

    public void dispose() {
        disposable.dispose();
    }
}
