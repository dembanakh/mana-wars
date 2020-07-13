package com.mana_wars.model.entity.user;

import io.reactivex.subjects.Subject;

public interface UserShopAPI extends UserBaseAPI{
    Subject<Integer> getManaAmountObservable();
    Subject<Integer> getUserLevelObservable();
    void updateManaAmount(int delta);
    void updateSkillCases(int delta);
}
