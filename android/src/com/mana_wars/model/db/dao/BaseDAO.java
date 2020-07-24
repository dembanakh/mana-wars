package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class BaseDAO<T> {

    public abstract Single<T> getEntityByID(int id);

    public abstract Single<List<T>> getEntitiesByIDs(List<Integer> ids);

    public abstract Single<List<T>> getAllEntities();

    @Insert
    public abstract Long insertEntity(T entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertEntities(List<T> entities);

    @Update
    public abstract int updateEntity(T entity);

    @Update
    public abstract int updateEntities(List<T> entities);

    @Delete
    public abstract int deleteEntity(T entity);
}
