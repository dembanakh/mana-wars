package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.db.entity.UserSkill;

import java.util.List;

@Dao
public abstract class UserSkillsDAO extends BaseDAO<UserSkill> {


    @Override
    @Query("SELECT * FROM user_skills WHERE user_skill_id=:id")
    public abstract UserSkill getEntityByID(int id);

    @Override
    @Query("SELECT * FROM user_skills")
    public abstract List<UserSkill> getAllEntities();

    @Transaction
    @Query("SELECT * FROM user_skills JOIN skills s ON skill_ref_id=s.skill_id ORDER BY s.rarity DESC, s.name, lvl DESC")
    public abstract List<CompleteUserSkill> getUserSkills();

    @Transaction
    public boolean mergeUserSkills(UserSkill toUpdate, UserSkill toDelete) {
        return (deleteEntity(toDelete) > 0) & (updateEntity(toUpdate) > 0);
    }
}
