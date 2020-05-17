package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import com.mana_wars.model.db.entity.UserSkill;
import com.mana_wars.model.db.entity.CompleteUserSkill;

@Dao
public abstract class UserSkillsDAO extends BaseDAO<UserSkill>{


    @Override
    @Query("SELECT * FROM user_skills WHERE user_skill_id=:id")
    public abstract UserSkill getEntityByID(int id);

    @Override
    @Query("SELECT * FROM user_skills")
    public abstract List<UserSkill> getAllEntities();

    @Transaction
    @Query("SELECT * FROM user_skills")
    public abstract List<CompleteUserSkill> getUserSkills();

}
