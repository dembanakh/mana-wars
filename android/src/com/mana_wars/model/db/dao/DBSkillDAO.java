package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;

import java.util.List;

@Dao
public abstract class DBSkillDAO extends BaseDAO<DBSkill> {

    @Override
    @Query("SELECT * FROM skills WHERE skill_id=:id")
    public abstract DBSkill getEntityByID(int id);

    @Override
    @Query("SELECT * FROM skills")
    public abstract List<DBSkill> getAllEntities();

    @Transaction
    @Query("SELECT * FROM skills")
    public abstract List<DBSkillWithCharacteristics> getSkillsWithCharacteristics();
}
