package com.mana_wars.model.repository;

public interface LocalUserDataRepository extends UsernameRepository, UserLevelExperienceRepository,
        UserManaRepository, ManaBonusRepository, UserSkillCasesRepository {
    int getDBversion();
    void setDBversion(int version);
}
