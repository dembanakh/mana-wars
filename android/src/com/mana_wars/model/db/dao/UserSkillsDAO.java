package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;
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
    @Query("SELECT * FROM user_skills")
    public abstract Single<List<UserSkill>> getAllEntities();

    @Transaction
    @Query("SELECT * FROM user_skills JOIN skills s ON skill_ref_id=s.skill_id ORDER BY s.rarity DESC, s.name, lvl DESC")
    public abstract Single<List<CompleteUserSkill>> getUserSkills();

    @Transaction
    public boolean mergeUserSkills(UserSkill toUpdate, UserSkill toDelete) {
        return (deleteEntity(toDelete) > 0) & (updateEntity(toUpdate) > 0);
    }

    @Query("SELECT SUM(sc.value) FROM user_skills us " +
            "JOIN skills s ON us.skill_ref_id=s.skill_id " +
            "JOIN skill_characteristic sc ON sc.skill_id=s.skill_id " +
            "WHERE s.is_active=0 AND us.chosen_id>0 AND sc.change_type=0 AND sc.type=2")
    public abstract Single<Integer> getRequiredManaAmountForBattle();
}
