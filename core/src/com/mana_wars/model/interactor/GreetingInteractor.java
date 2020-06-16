package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.user.UserGreetingAPI;
import com.mana_wars.model.repository.UsernameRepository;

public class GreetingInteractor extends BaseInteractor{

    private final UserGreetingAPI user;

    public GreetingInteractor(final UserGreetingAPI user) {
        this.user = user;
    }

    public boolean hasUsername() {
        return user.getName() != null;
    }

    public void registerUser(String username) {
        if (!hasUsername()) {
            user.setName(username);
        } else {
            // something went wrong
            System.out.println("Has username");
        }
    }

}
