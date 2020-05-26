package com.mana_wars.model.interactor;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;

import io.reactivex.disposables.CompositeDisposable;

public class GreetingInteractor {

    private LocalUserDataRepository localUserDataRepository;
    private DatabaseRepository databaseRepository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public GreetingInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository){
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
    }

    public void registerUser(String username) {
        System.out.println(username);
    }

    public void dispose(){
        disposable.dispose();
    }

}
