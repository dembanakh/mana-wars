package com.mana_wars.model.repository;

public interface LocalUserDataRepository {
    int getDBversion();
    void setDBversion(int version);
}
