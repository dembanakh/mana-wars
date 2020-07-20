package com.mana_wars.model.entity.user;

import io.reactivex.Observable;

public interface UserSkillsAPI extends UserBaseAPI{
    Observable<Integer> getManaAmountObservable();
    Observable<Integer> getUserLevelObservable();
    Observable<Integer> getExperienceObservable();
    Observable<Integer> getNextLevelRequiredExperienceObservable();
}
