package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mana_wars.model.db.entity.base.DBMob;
import com.mana_wars.model.db.entity.query.DBMobWithSkills;

import java.util.List;

import io.reactivex.Single;


@Dao
public abstract class DBMobDAO extends BaseDAO<DBMob> {

    @Override
    @Query("SELECT * FROM mobs WHERE mob_id=:id")
    public abstract Single<DBMob> getEntityByID(int id);

    @Override
    @Query("SELECT * FROM mobs WHERE mob_id IN (:ids)")
    public abstract Single<List<DBMob>> getEntitiesByIDs(List<Integer> ids);

    @Override
    @Query("SELECT * FROM mobs")
    public abstract Single<List<DBMob>> getAllEntities();


    @Transaction
    @Query("SELECT * from mobs where dungeon_ref_id=:id")
    public abstract Single<List<DBMobWithSkills>> getDBMobsWithSkillsByDungeonID(int id);
}
