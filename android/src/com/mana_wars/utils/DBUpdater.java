package com.mana_wars.utils;

import android.util.Log;

import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class DBUpdater implements DBUpdaterParser.DBUpdater {

    private final RoomRepository repository;
    private final SharedPreferencesRepository preferences;

    private CompositeDisposable disposable = new CompositeDisposable();

    public interface Callback {
        void afterUpdate();
    }

    private Callback callback;

    private int updatedInstancesCount = 0;
    private final int instancesToUpdate = 6;

    public DBUpdater(RoomRepository repository, SharedPreferencesRepository preferences, Callback callback) {
        this.repository = repository;
        this.preferences = preferences;
        this.callback = callback;
    }

    @Override
    public void updateUserLvlRequiredExperience(String userLvlRequiredExperience) {
        preferences.setUserLvlRequiredExperience(userLvlRequiredExperience);
    }

    @Override
    public <T> void update(List<T> entities, DBUpdaterParser.DAOMapper<T> DAOMapper) {
        disposable.add(repository.insertEntities(entities, DAOMapper.get(repository)).subscribe(
                this::completeUpdate,
                Throwable::printStackTrace
        ));
    }

    private synchronized void completeUpdate() {
        Log.i("UPDATER", "updated=" + updatedInstancesCount);
        if (++updatedInstancesCount == instancesToUpdate) {
            disposable.dispose();
            callback.afterUpdate();
        }
    }
}
