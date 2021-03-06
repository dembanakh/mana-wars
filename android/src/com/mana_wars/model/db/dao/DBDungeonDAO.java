package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mana_wars.model.db.entity.base.DBDungeon;
import com.mana_wars.model.db.entity.query.DBDungeonWithRoundsDescription;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class DBDungeonDAO extends BaseDAO<DBDungeon> {

    @Override
    @Query("SELECT * FROM dungeons WHERE dungeon_id=:id")
    public abstract Single<DBDungeon> getEntityByID(int id);

    @Override
    @Query("SELECT * FROM dungeons WHERE dungeon_id IN (:ids)")
    public abstract Single<List<DBDungeon>> getEntitiesByIDs(List<Integer> ids);

    @Override
    @Query("SELECT * FROM dungeons")
    public abstract Single<List<DBDungeon>> getAllEntities();

    @Transaction
    @Query("SELECT * FROM dungeons")
    public abstract Single<List<DBDungeonWithRoundsDescription>> getDBDungeonsWithRoundsDescription();
}
