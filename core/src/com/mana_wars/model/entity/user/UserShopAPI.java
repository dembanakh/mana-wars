package com.mana_wars.model.entity.user;

import io.reactivex.subjects.Subject;

public interface UserShopAPI {
    Subject<Integer> getManaAmountObservable();
    Subject<Integer> getUserLevelObservable();
    void updateManaAmount(int delta);
}
