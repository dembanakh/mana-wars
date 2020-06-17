package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class DBSkillDAO extends BaseDAO<DBSkill> {

    @Override
    @Query("SELECT * FROM skills WHERE skill_id=:id")
    public abstract Single<DBSkill> getEntityByID(int id);

    @Override
    @Query("SELECT * FROM skills")
    public abstract Single<List<DBSkill>> getAllEntities();

    @Transaction
    @Query("SELECT * FROM skills")
    public abstract Single<List<DBSkillWithCharacteristics>> getSkillsWithCharacteristics();
}
