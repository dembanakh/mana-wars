package com.mana_wars.model.repository;

public interface LocalUserDataRepository {

    boolean getIsFirstOpen();
    void setIsFirstOpen(boolean flag);

    int getDBversion();
    void setDBversion(int version);
}
