package com.mana_wars.model.entity.user;

import io.reactivex.subjects.Subject;

public interface UserMenuAPI {
    void updateManaAmount(int delta);
    String getName();
    Subject<Integer> getManaAmountObservable();
    Subject<Integer> getUserLevelObservable();
}
