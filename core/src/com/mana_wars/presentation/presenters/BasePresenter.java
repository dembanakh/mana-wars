package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.user.UserBaseAPI;
import com.mana_wars.model.interactor.BaseInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<U extends BaseView, T extends BaseInteractor <? extends UserBaseAPI>> {

    protected final U view;
    protected final T interactor;

    protected final UIThreadHandler uiThreadHandler;
    protected final CompositeDisposable disposable = new CompositeDisposable();

    public BasePresenter(U view, T interactor, UIThreadHandler handler) {
        this.view = view;
        this.interactor = interactor;
        this.uiThreadHandler = handler;
    }

    public static BasePresenter<BaseView, BaseInteractor<UserBaseAPI>> Default(BaseView view, UIThreadHandler handler) {
        return new BasePresenter<BaseView, BaseInteractor<UserBaseAPI>>(
                view, new BaseInteractor<UserBaseAPI>(null) {}, handler){};
    }

    public void dispose() {
        disposable.dispose();
        interactor.dispose();
    }
}
