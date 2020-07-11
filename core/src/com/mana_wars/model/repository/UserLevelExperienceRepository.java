package com.mana_wars.model.repository;

import java.util.List;

public interface UserLevelExperienceRepository {
    int getUserLevel();
    List<Integer> getUserLevelRequiredExperience();
    void setUserLevel(int level);
    int getCurrentUserExperience();
    void setCurrentUserExperience(int currentUserExperience);
}
