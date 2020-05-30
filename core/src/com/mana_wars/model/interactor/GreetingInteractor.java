package com.mana_wars.model.interactor;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.UsernameRepository;

import io.reactivex.disposables.CompositeDisposable;

public class GreetingInteractor {

    private UsernameRepository usernameRepository;
    private DatabaseRepository databaseRepository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public GreetingInteractor(UsernameRepository usernameRepository, DatabaseRepository databaseRepository) {
        this.usernameRepository = usernameRepository;
        this.databaseRepository = databaseRepository;
    }

    public void registerUser(String username) {
        if (!usernameRepository.hasUsername()) {
            usernameRepository.setUsername(username);
        } else {
            // something went wrong
            System.out.println("Has username");
        }
    }

    public void dispose(){
        disposable.dispose();
    }

    public boolean hasUsername() {
        return usernameRepository.hasUsername();
    }
}
