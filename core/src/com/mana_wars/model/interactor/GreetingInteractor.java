package com.mana_wars.model.interactor;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.UsernameRepository;

import io.reactivex.disposables.CompositeDisposable;

public class GreetingInteractor extends BaseInteractor{

    private UsernameRepository usernameRepository;
    private DatabaseRepository databaseRepository;

    public GreetingInteractor(UsernameRepository usernameRepository, DatabaseRepository databaseRepository) {
        this.usernameRepository = usernameRepository;
        this.databaseRepository = databaseRepository;
    }

    public boolean hasUsername() {
        return usernameRepository.hasUsername();
    }

    public void registerUser(String username) {
        if (!usernameRepository.hasUsername()) {
            usernameRepository.setUsername(username);
        } else {
            // something went wrong
            System.out.println("Has username");
        }
    }

}
