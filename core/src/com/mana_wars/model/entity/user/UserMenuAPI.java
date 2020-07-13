package com.mana_wars.model.entity.user;

import io.reactivex.subjects.Subject;

public interface UserMenuAPI extends UserBaseAPI{
    void updateManaAmount(int delta);
    void updateSkillCases(int delta);

    String getName();
    Subject<Integer> getManaAmountObservable();
    Subject<Integer> getUserLevelObservable();
    int getSkillCasesNumber();
}
