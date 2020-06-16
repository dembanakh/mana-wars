package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mana_wars.model.db.entity.DBMob;
import com.mana_wars.model.db.entity.DBMobWithSkills;

import java.util.List;


@Dao
public abstract class DBMobDAO extends BaseDAO<DBMob> {

    @Override
    @Query("SELECT * FROM mobs WHERE mob_id=:id")
    public abstract DBMob getEntityByID(int id);

    @Override
    @Query("SELECT * FROM mobs")
    public abstract List<DBMob> getAllEntities();


    @Transaction
    @Query("SELECT * from mobs where dungeon_ref_id=:id")
    public abstract List<DBMobWithSkills> getDBMobsWithSkillsByDungeonID(int id);
}
