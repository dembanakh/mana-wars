package com.mana_wars.model.repository;

public interface LocalUserDataRepository extends UsernameRepository {
    int getDBversion();
    void setDBversion(int version);
}
