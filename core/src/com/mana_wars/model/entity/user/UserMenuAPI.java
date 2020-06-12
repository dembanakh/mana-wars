package com.mana_wars.model.entity.user;

import io.reactivex.Single;
import io.reactivex.subjects.Subject;

public interface UserMenuAPI {
    Subject<Integer> getManaAmountObservable();
    Subject<Integer> getUserLevelObservable();
    void updateManaAmount(int delta);
    String getName();
}
