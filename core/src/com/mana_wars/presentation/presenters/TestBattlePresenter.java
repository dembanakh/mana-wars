package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.interactor.TestBattleInteractor;
import com.mana_wars.presentation.view.TestBattleView;

import io.reactivex.disposables.CompositeDisposable;

public class TestBattlePresenter {

    private final TestBattleView view;
    private final TestBattleInteractor interactor;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public TestBattlePresenter(TestBattleView view, TestBattleInteractor interactor){
        this.view = view;
        this.interactor = interactor;
    }

    public void onButtonPress() {
        view.setLabelText("HELLO, UJ");
    }

    public void dispose(){
        disposable.dispose();
        interactor.dispose();
    }

}
