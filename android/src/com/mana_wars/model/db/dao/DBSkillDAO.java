package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mana_wars.model.db.entity.base.DBSkill;
import com.mana_wars.model.db.entity.query.DBSkillWithCharacteristics;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class DBSkillDAO extends BaseDAO<DBSkill> {

    @Override
    @Query("SELECT * FROM skills WHERE skill_id=:id")
    public abstract Single<DBSkill> getEntityByID(int id);

    @Override
    @Query("SELECT * FROM skills WHERE skill_id IN (:ids)")
    public abstract Single<List<DBSkill>> getEntitiesByIDs(List<Integer> ids);

    @Override
    @Query("SELECT * FROM skills")
    public abstract Single<List<DBSkill>> getAllEntities();

    @Transaction
    @Query("SELECT * FROM skills")
    public abstract Single<List<DBSkillWithCharacteristics>> getSkillsWithCharacteristics();

    @Transaction
    @Query("SELECT * FROM skills WHERE skill_id IN (:ids)")
    public abstract Single<List<DBSkillWithCharacteristics>> getSkillsWithCharacteristicsByIDs(List<Integer> ids);
}
