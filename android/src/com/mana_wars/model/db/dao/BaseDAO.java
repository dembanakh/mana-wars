package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class BaseDAO <T> {

    public abstract T getEntityByID(int id);

    public abstract List<T> getAllEntities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertEntities(List<T> entities);

    @Insert
    public abstract Long insertEntity(T entity);

    @Delete
    public abstract void deleteEntity(T entity);

    @Update
    public abstract void updateEntity(T entity);
}
