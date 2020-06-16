package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.mana_wars.model.db.entity.DBMobSkill;

import java.util.List;

@Dao
public abstract class DBMobSkillDAO extends BaseDAO<DBMobSkill> {

    @Override
    @Query("SELECT * FROM mobs_skills WHERE mob_skill_id=:id")
    public abstract DBMobSkill getEntityByID(int id);

    @Override
    @Query("SELECT * FROM mobs_skills")
    public abstract List<DBMobSkill> getAllEntities();

}
