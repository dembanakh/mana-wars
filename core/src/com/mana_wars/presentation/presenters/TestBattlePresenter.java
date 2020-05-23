package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.interactor.TestBattleInteractor;
import com.mana_wars.presentation.view.TestBattleView;

import io.reactivex.disposables.CompositeDisposable;

public class TestBattlePresenter {

    private TestBattleView view;
    private TestBattleInteractor interactor;
    private CompositeDisposable disposable = new CompositeDisposable();

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
