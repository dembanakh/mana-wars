package com.mana_wars.model.repository;

import android.content.Context;

import com.mana_wars.model.db.AppDatabase;
import com.mana_wars.model.db.dao.BaseDAO;
import com.mana_wars.model.db.dao.DBDungeonDAO;
import com.mana_wars.model.db.dao.DBDungeonRoundDescriptionDAO;
import com.mana_wars.model.db.dao.DBMobDAO;
import com.mana_wars.model.db.dao.DBMobSkillDAO;
import com.mana_wars.model.db.dao.DBSkillCharacteristicDAO;
import com.mana_wars.model.db.dao.DBSkillDAO;
import com.mana_wars.model.db.dao.UserSkillsDAO;
import com.mana_wars.model.db.entity.query.CompleteUserSkill;
import com.mana_wars.model.db.entity.query.DBDungeonWithRoundsDescription;
import com.mana_wars.model.db.entity.query.DBMobWithSkills;
import com.mana_wars.model.db.entity.query.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.query.UserSkill;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomRepository {

    private static RoomRepository instance;

    final UserSkillsDAO userSkillsDAO;
    public final DBSkillDAO dbSkillDAO;
    public final DBSkillCharacteristicDAO dbSkillCharacteristicDAO;
    public final DBDungeonDAO dbDungeonDAO;
    public final DBMobDAO dbMobDAO;
    public final DBMobSkillDAO dbMobSkillDAO;
    public final DBDungeonRoundDescriptionDAO dbDungeonRoundDescriptionDAO;

    public static synchronized RoomRepository getInstance(Context context) {
        if (instance == null) {
            AppDatabase db = AppDatabase.getDatabase(context);
            instance = new RoomRepository(db.userSkillsDAO(), db.dbSkillDAO(),
                    db.dbSkillCharacteristicDAO(), db.dbDungeonDAO(), db.dbMobDAO(), db.dbMobSkillDAO(), db.dbDungeonRoundDescriptionDAO());
        }
        return instance;
    }

    public <T> Single<List<T>> getAllEntities(BaseDAO<T> dao) {
        return dao.getAllEntities();
    }

    public <T> Single<List<T>> getEntitiesByIDs(BaseDAO<T> dao, List<Integer> ids) {
        return dao.getEntitiesByIDs(ids);
    }

    Single<List<DBDungeonWithRoundsDescription>> getDBDungeonsWithRoundsDescription() {
        return dbDungeonDAO.getDBDungeonsWithRoundsDescription();
    }

    Single<List<DBSkillWithCharacteristics>> getSkillsWithCharacteristics() {
        return dbSkillDAO.getSkillsWithCharacteristics();
    }

    Single<List<DBSkillWithCharacteristics>> getSkillsWithCharacteristicsByIDs(List<Integer> ids) {
        return dbSkillDAO.getSkillsWithCharacteristicsByIDs(ids);
    }

    Single<List<CompleteUserSkill>> getCompleteUserSkills() {
        return userSkillsDAO.getUserSkills();
    }

    Single<List<DBMobWithSkills>> getDBMobsWithSkillsByDungeonID(int id) {
        return dbMobDAO.getDBMobsWithSkillsByDungeonID(id);
    }

    Single<List<CompleteUserSkill>> getChosenPassiveSkills() {
        return userSkillsDAO.getChosenPassiveSkills();
    }

    private RoomRepository(UserSkillsDAO userSkillsDAO, DBSkillDAO dbSkillDAO,
                           DBSkillCharacteristicDAO dbSkillCharacteristicDAO,
                           DBDungeonDAO dbDungeonDAO, DBMobDAO dbMobDAO, DBMobSkillDAO dbMobSkillDAO, DBDungeonRoundDescriptionDAO dbDungeonRoundDescriptionDAO) {
        this.userSkillsDAO = userSkillsDAO;
        this.dbSkillDAO = dbSkillDAO;
        this.dbSkillCharacteristicDAO = dbSkillCharacteristicDAO;
        this.dbDungeonDAO = dbDungeonDAO;
        this.dbMobDAO = dbMobDAO;
        this.dbMobSkillDAO = dbMobSkillDAO;
        this.dbDungeonRoundDescriptionDAO = dbDungeonRoundDescriptionDAO;
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

    Completable mergeSkills(UserSkill toUpdate, UserSkill toDelete) {
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
