package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.mana_wars.model.db.entity.DBDungeon;

import java.util.List;

@Dao
public abstract class DBDungeonDAO extends BaseDAO<DBDungeon> {

    @Override
    @Query("SELECT * FROM dungeons WHERE dungeon_id=:id")
    public abstract DBDungeon getEntityByID(int id);

    @Override
    @Query("SELECT * FROM dungeons")
    public abstract List<DBDungeon> getAllEntities();

}
