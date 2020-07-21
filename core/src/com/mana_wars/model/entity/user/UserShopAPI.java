package com.mana_wars.model.entity.user;

import io.reactivex.Observable;

public interface UserShopAPI extends UserBaseAPI{
    Observable<Integer> getManaAmountObservable();
    Observable<Integer> getUserLevelObservable();
    Observable<Integer> getExperienceObservable();
    Observable<Integer> getNextLevelRequiredExperienceObservable();
    void updateManaAmount(int delta);
    int updateSkillCases(int delta);
    int getSkillCasesNumber();
}
