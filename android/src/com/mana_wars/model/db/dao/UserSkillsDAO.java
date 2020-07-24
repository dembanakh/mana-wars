package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.db.entity.UserSkill;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class UserSkillsDAO extends BaseDAO<UserSkill> {


    @Override
    @Query("SELECT * FROM user_skills WHERE user_skill_id=:id")
    public abstract Single<UserSkill> getEntityByID(int id);

    @Override
    @Query("SELECT * FROM user_skills WHERE user_skill_id IN (:ids)")
    public abstract Single<List<UserSkill>> getEntitiesByIDs(List<Integer> ids);

    @Override
    @Query("SELECT * FROM user_skills")
    public abstract Single<List<UserSkill>> getAllEntities();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM user_skills JOIN skills s ON skill_ref_id=s.skill_id ORDER BY s.rarity DESC, s.name, lvl DESC")
    public abstract Single<List<CompleteUserSkill>> getUserSkills();

    @Transaction
    public boolean mergeUserSkills(UserSkill toUpdate, UserSkill toDelete) {
        return (deleteEntity(toDelete) > 0) & (updateEntity(toUpdate) > 0);
    }

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM user_skills us JOIN skills s ON us.skill_ref_id=s.skill_id WHERE s.is_active=0 AND us.chosen_id>0")
    public abstract Single<List<CompleteUserSkill>> getChosenPassiveSkills();
}
