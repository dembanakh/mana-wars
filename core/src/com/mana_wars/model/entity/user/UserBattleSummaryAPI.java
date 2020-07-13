package com.mana_wars.model.entity.user;

public interface UserBattleSummaryAPI extends UserBaseAPI {
    void updateManaAmount(int delta);
    void updateExperience(int delta);
    void updateSkillCases(int delta);
}
