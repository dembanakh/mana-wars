package com.mana_wars.model.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.mana_wars.model.db.entity.DBSkillCharacteristic;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class DBSkillCharacteristicDAO extends BaseDAO<DBSkillCharacteristic> {

    @Override
    @Query("SELECT * FROM skill_characteristic WHERE skill_char_id=:id")
    public abstract Single<DBSkillCharacteristic> getEntityByID(int id);

    @Override
    @Query("SELECT * FROM skill_characteristic")
    public abstract Single<List<DBSkillCharacteristic>> getAllEntities();

}
