package com.mana_wars.utils;

import android.util.Log;

import com.mana_wars.model.db.entity.DBDungeon;
import com.mana_wars.model.db.entity.DBMob;
import com.mana_wars.model.db.entity.DBMobSkill;
import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.DBSkillCharacteristic;
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
    private final int instancesToUpdate = 5;

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
    public void updateSkills(List<DBSkill> skills) {
        disposable.add(repository.insertEntities(skills, repository.dbSkillDAO).subscribe(
                this::completeUpdate,
                Throwable::printStackTrace
        ));

    }

    @Override
    public void updateCharacteristics(List<DBSkillCharacteristic> characteristics) {
        disposable.add(repository.insertEntities(characteristics, repository.dbSkillCharacteristicDAO).subscribe(
                this::completeUpdate,
                Throwable::printStackTrace
        ));
    }

    @Override
    public void updateDungeons(List<DBDungeon> dungeons) {
        disposable.add(repository.insertEntities(dungeons, repository.dbDungeonDAO).subscribe(
                this::completeUpdate,
                Throwable::printStackTrace
        ));
    }

    @Override
    public void updateMobs(List<DBMob> mobs) {
        disposable.add(repository.insertEntities(mobs, repository.dbMobDAO).subscribe(
                this::completeUpdate,
                Throwable::printStackTrace
        ));
    }

    @Override
    public void updateMobsSkills(List<DBMobSkill> mobSkills) {
        disposable.add(repository.insertEntities(mobSkills, repository.dbMobSkillDAO).subscribe(
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
