package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.user.UserGreetingAPI;

public class GreetingInteractor extends BaseInteractor<UserGreetingAPI> {

    public GreetingInteractor(final UserGreetingAPI user) {
        super(user);
    }

    public boolean hasUsername() {
        return user.getName() != null;
    }

    public void registerUser(String username) {
        if (!hasUsername()) {
            user.setName(username);
            user.updateManaAmount(1000);
            user.checkNextLevel();
        } else {
            throw new IllegalStateException("User has already got a username");
        }
    }
}
