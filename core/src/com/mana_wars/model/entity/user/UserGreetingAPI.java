package com.mana_wars.model.entity.user;

public interface UserGreetingAPI {
    String getName();
    void setName(String name);
    void updateManaAmount(int delta);
    void checkNextLevel();
}
