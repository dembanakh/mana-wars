package com.mana_wars.model.interactor;

import com.mana_wars.model.repository.UsernameRepository;

public class GreetingInteractor extends BaseInteractor{

    private UsernameRepository usernameRepository;

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

}
