package com.mana_wars.model.entity.user;

import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

public interface UserMenuAPI extends UserBaseAPI{
    void updateManaAmount(int delta);
    int updateSkillCases(int delta);

    String getName();
    Observable<Integer> getManaAmountObservable();
    Observable<Integer> getLevelObservable();
    Observable<Integer> getExperienceObservable();
    Observable<Integer> getNextLevelRequiredExperienceObservable();
    int getSkillCasesNumber();
}
