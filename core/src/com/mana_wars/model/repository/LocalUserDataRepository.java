package com.mana_wars.model.repository;

public interface LocalUserDataRepository extends UsernameRepository, UserLevelRepository,
        UserManaRepository, ManaBonusRepository {
    int getDBversion();
    void setDBversion(int version);
}
