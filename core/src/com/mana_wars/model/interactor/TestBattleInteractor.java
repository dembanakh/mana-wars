package com.mana_wars.model.interactor;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;

import io.reactivex.disposables.CompositeDisposable;

public class TestBattleInteractor {

    private LocalUserDataRepository localUserDataRepository;
    private DatabaseRepository databaseRepository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public TestBattleInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository){
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
    }

    public void dispose() {
        disposable.dispose();
    }
}
