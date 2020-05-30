package com.mana_wars.model.repository;

public interface UsernameRepository {
    boolean hasUsername();
    String getUsername();
    void setUsername(String username);
}
