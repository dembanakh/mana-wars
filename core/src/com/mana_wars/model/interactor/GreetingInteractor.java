package com.mana_wars.model.interactor;

import com.mana_wars.model.repository.UsernameRepository;

import io.reactivex.disposables.CompositeDisposable;

public class GreetingInteractor {

    private UsernameRepository usernameRepository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public GreetingInteractor(UsernameRepository usernameRepository) {
        this.usernameRepository = usernameRepository;
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

    public void dispose(){
        disposable.dispose();
    }

}
