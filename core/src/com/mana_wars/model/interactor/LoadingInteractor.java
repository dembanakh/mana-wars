package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.user.UserBaseAPI;
import com.mana_wars.model.repository.ApplicationDataUpdater;

import io.reactivex.Completable;

public class LoadingInteractor extends BaseInteractor<UserBaseAPI>{

    private final ApplicationDataUpdater updater;

    public LoadingInteractor(ApplicationDataUpdater updater) {
        super(null);
        this.updater = updater;
    }

    public <T> Completable checkApplicationUpdate() {
        return updater.checkFullUpdate();
    }
}
