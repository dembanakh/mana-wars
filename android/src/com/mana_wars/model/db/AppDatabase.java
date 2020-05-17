package com.mana_wars.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mana_wars.model.db.dao.DBSkillCharacteristicDAO;
import com.mana_wars.model.db.dao.DBSkillDAO;
import com.mana_wars.model.db.dao.UserSkillsDAO;
import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.DBSkillCharacteristic;
import com.mana_wars.model.db.entity.UserSkill;

@Database(entities = {UserSkill.class, DBSkill.class, DBSkillCharacteristic.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase database;

    public abstract UserSkillsDAO userSkillsDAO();
    public abstract DBSkillDAO dbSkillDAO();
    public abstract DBSkillCharacteristicDAO dbSkillCharacteristicDAO();

    public static AppDatabase getDatabase(Context context){
        synchronized(AppDatabase.class) {
            if(database == null) {
                database = Room.databaseBuilder(context, AppDatabase.class, "MANA_WARS_DB")
                        .build();
            }
        }
        return database;
    }

}
