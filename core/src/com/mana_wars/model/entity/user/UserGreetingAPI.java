package com.mana_wars.model.entity.user;

public interface UserGreetingAPI extends UserBaseAPI{
    String getName();
    void setName(String name);
    void updateManaAmount(int delta);
    void checkNextLevel();
}
