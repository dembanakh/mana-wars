package com.mana_wars.presentation.presenters;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.interactor.BaseInteractor;
import com.mana_wars.model.interactor.ShopInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.ShopView;

import io.reactivex.functions.Consumer;

public final class ShopPresenter extends BasePresenter<ShopView, ShopInteractor> {

    public ShopPresenter(ShopView view, ShopInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void addObserver_manaAmount(Consumer<? super Integer> observer) {
        disposable.add(interactor.getManaAmountObservable().subscribe(observer));
    }

    public void addObserver_userLevel(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserLevelObservable().subscribe(observer));
    }

    public void buySkillCase() {
        // obtain cases
        interactor.buySkillCase();
    }

}
