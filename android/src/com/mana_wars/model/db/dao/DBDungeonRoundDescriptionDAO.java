package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.mana_wars.model.db.entity.base.DBDungeonRoundDescription;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class DBDungeonRoundDescriptionDAO extends BaseDAO<DBDungeonRoundDescription> {

    @Override
    @Query("SELECT * FROM dungeon_rounds WHERE drd_id=:id")
    public abstract Single<DBDungeonRoundDescription> getEntityByID(int id);

    @Override
    @Query("SELECT * FROM dungeon_rounds WHERE drd_id IN (:ids)")
    public abstract Single<List<DBDungeonRoundDescription>> getEntitiesByIDs(List<Integer> ids);

    @Override
    @Query("SELECT * FROM dungeon_rounds")
    public abstract Single<List<DBDungeonRoundDescription>> getAllEntities();
}
