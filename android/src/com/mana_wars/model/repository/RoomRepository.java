package com.mana_wars.model.repository;

import android.content.Context;

import com.mana_wars.model.db.AppDatabase;
import com.mana_wars.model.db.dao.BaseDAO;
import com.mana_wars.model.db.dao.DBDungeonDAO;
import com.mana_wars.model.db.dao.DBMobDAO;
import com.mana_wars.model.db.dao.DBMobSkillDAO;
import com.mana_wars.model.db.dao.DBSkillCharacteristicDAO;
import com.mana_wars.model.db.dao.DBSkillDAO;
import com.mana_wars.model.db.dao.UserSkillsDAO;
import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.db.entity.DBMobWithSkills;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.UserSkill;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomRepository {

    private static RoomRepository instance;

    public final UserSkillsDAO userSkillsDAO;
    public final DBSkillDAO dbSkillDAO;
    public final DBSkillCharacteristicDAO dbSkillCharacteristicDAO;
    public final DBDungeonDAO dbDungeonDAO;
    public final DBMobDAO dbMobDAO;
    public final DBMobSkillDAO dbMobSkillDAO;

    public static RoomRepository getInstance(Context context) {
        if (instance == null) {
            AppDatabase db = AppDatabase.getDatabase(context);
            instance = new RoomRepository(db.userSkillsDAO(), db.dbSkillDAO(),
                    db.dbSkillCharacteristicDAO(), db.dbDungeonDAO(), db.dbMobDAO(), db.dbMobSkillDAO());
        }
        return instance;
    }

    public <T> Single<List<T>> getAllEntities(BaseDAO<T> dao) {
        return Single.create((SingleOnSubscribe<List<T>>) emitter -> {
            try {
                emitter.onSuccess(dao.getAllEntities());
            } catch (Exception e) {
                emitter.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<DBSkillWithCharacteristics>> getSkillsWithCharacteristics() {
        return Single.create((SingleOnSubscribe<List<DBSkillWithCharacteristics>>) emitter -> {
            try {
                emitter.onSuccess(dbSkillDAO.getSkillsWithCharacteristics());
            } catch (Exception e) {
                emitter.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<CompleteUserSkill>> getCompleteUserSkills() {
        return Single.create((SingleOnSubscribe<List<CompleteUserSkill>>) emitter -> {
            try {
                emitter.onSuccess(userSkillsDAO.getUserSkills());
            } catch (Exception e) {
                emitter.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<DBMobWithSkills>> getDBMobsWithSkillsByDungeonID(int id) {
        return Single.create((SingleOnSubscribe<List<DBMobWithSkills>>) emitter -> {
            try {
                emitter.onSuccess(dbMobDAO.getDBMobsWithSkillsByDungeonID(id));
            } catch (Exception e) {
                emitter.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private RoomRepository(UserSkillsDAO userSkillsDAO, DBSkillDAO dbSkillDAO,
                           DBSkillCharacteristicDAO dbSkillCharacteristicDAO,
                           DBDungeonDAO dbDungeonDAO, DBMobDAO dbMobDAO, DBMobSkillDAO dbMobSkillDAO) {
        this.userSkillsDAO = userSkillsDAO;
        this.dbSkillDAO = dbSkillDAO;
        this.dbSkillCharacteristicDAO = dbSkillCharacteristicDAO;
        this.dbDungeonDAO = dbDungeonDAO;
        this.dbMobDAO = dbMobDAO;
        this.dbMobSkillDAO = dbMobSkillDAO;
    }

    public <T> Completable insertEntities(final List<T> entities, final BaseDAO<T> dao) {
        return multithreading(Completable.fromAction(
                () -> dao.insertEntities(entities)
        ));
    }

    public <T> Completable insertEntity(final T entity, final BaseDAO<T> dao) {
        return multithreading(Completable.fromAction(
                () -> dao.insertEntity(entity)
        ));
    }

    public <T> Completable updateEntities(final List<T> entities, final BaseDAO<T> dao) {
        return multithreading(Completable.fromAction(
                () -> dao.updateEntities(entities)
        ));
    }

    public <T> Completable updateEntity(final T entity, final BaseDAO<T> dao) {
        return multithreading(Completable.fromAction(
                () -> dao.updateEntity(entity)
        ));
    }

    public Completable mergeSkills(UserSkill toUpdate, UserSkill toDelete) {
        return multithreading(Completable.fromAction(
                () -> userSkillsDAO.mergeUserSkills(toUpdate, toDelete)
        ));
    }

    private Completable multithreading(Completable completable) {
        return completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
