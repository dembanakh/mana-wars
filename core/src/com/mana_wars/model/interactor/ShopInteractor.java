package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.user.UserShopAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.subjects.Subject;

public final class ShopInteractor extends BaseInteractor {

    private final UserShopAPI user;

    private final DatabaseRepository databaseRepository;

    public ShopInteractor(final UserShopAPI user, final DatabaseRepository databaseRepository) {

        this.user = user;
        this.databaseRepository = databaseRepository;
    }

    public Subject<Integer> getManaAmountObservable() {
        return user.getManaAmountObservable();
    }

    public Subject<Integer> getUserLevelObservable() {
        return user.getUserLevelObservable();
    }

    public void updateManaAmount(int delta) {
        user.updateManaAmount(delta);
    }
    
}
