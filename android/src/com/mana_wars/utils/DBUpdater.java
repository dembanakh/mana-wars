package com.mana_wars.utils;

import android.util.Log;

import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.DBSkillCharacteristic;
import com.mana_wars.model.repository.RoomRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class DBUpdater implements DBUpdaterParser.DBUpdater {

    private RoomRepository repository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public interface Callback {
        void afterUpdate();
    }
    private Callback callback;

    private int updatedInstancesCount = 0;
    private final int instancesToUpdate = 2;

    public DBUpdater(RoomRepository repository, Callback callback){
        this.repository = repository;
        this.callback = callback;
    }

    @Override
    public void insertSkills(List<DBSkill> skills) {
        disposable.add(repository.insertEntities(skills, repository.dbSkillDAO).subscribe(
                this::completeUpdate,
                Throwable::printStackTrace
        ));

    }

    @Override
    public void insertCharacteristics(List<DBSkillCharacteristic> characteristics) {
        disposable.add(repository.insertEntities(characteristics, repository.dbSkillCharacteristicDAO).subscribe(
                this::completeUpdate,
                Throwable::printStackTrace
        ));
    }

    private synchronized void completeUpdate(){
        Log.i("UPDATER", "updated=" + updatedInstancesCount);

        if(++updatedInstancesCount==instancesToUpdate){
            disposable.dispose();
            callback.afterUpdate();
        }
    }
}
