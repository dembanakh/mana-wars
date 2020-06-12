package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.user.UserGreetingAPI;
import com.mana_wars.model.repository.UsernameRepository;

public class GreetingInteractor extends BaseInteractor{

    private final UserGreetingAPI user;
    private final UsernameRepository usernameRepository;

    public GreetingInteractor(final UserGreetingAPI user, final UsernameRepository usernameRepository) {
        this.user = user;
        this.usernameRepository = usernameRepository;
    }

    public boolean hasUsername() {
        return usernameRepository.hasUsername();
    }

    public void registerUser(String username) {
        if (!hasUsername()) {
            user.setName(username);
            usernameRepository.setUsername(username);
        } else {
            // something went wrong
            System.out.println("Has username");
        }
    }

}
