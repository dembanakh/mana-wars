package com.mana_wars.model.repository;

import io.reactivex.Completable;

public interface ApplicationDataUpdater {
    Completable checkFullUpdate();
}
