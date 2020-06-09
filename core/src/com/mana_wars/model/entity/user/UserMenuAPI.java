package com.mana_wars.model.entity.user;

import io.reactivex.subjects.Subject;

public interface UserMenuAPI {
    Subject<Integer> getManaAmountObservable();
    Subject<Integer> getUserLevelObservable();
    Subject<String> getUsernameObservable();
    void updateManaAmount(int delta);
}
