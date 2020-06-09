package com.mana_wars.model.entity.user;

import io.reactivex.subjects.Subject;

public interface UserSkillsAPI {
    Subject<Integer> getManaAmountObservable();
    Subject<Integer> getUserLevelObservable();
    Subject<String> getUsernameObservable();
}
