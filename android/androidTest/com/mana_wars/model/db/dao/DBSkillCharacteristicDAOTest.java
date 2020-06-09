package com.mana_wars.model.db.dao;

import android.content.Context;

import androidx.room.Room;


import com.mana_wars.model.db.AppDatabase;
import com.mana_wars.model.db.entity.DBSkillCharacteristic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class DBSkillCharacteristicDAOTest {

    private AppDatabase db;
    DBSkillCharacteristicDAO dao;
    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.dbSkillCharacteristicDAO();

    }

    @After
    public void closeDB(){
        db.close();
    }

    @Test
    public void getEntityByID() {

        DBSkillCharacteristic skill = new DBSkillCharacteristic();
        skill.setValue(10);
        skill.setId(1);
        dao.insertEntity(skill);

        DBSkillCharacteristic result = dao.getEntityByID(1);

        assertEquals(10, result.getValue());
        dao.deleteEntity(skill);
    }

    @Test
    public void getAllEntities() {
        DBSkillCharacteristic skill = new DBSkillCharacteristic();
        skill.setValue(10);
        skill.setId(1);
        dao.insertEntity(skill);

        DBSkillCharacteristic skill2 = new DBSkillCharacteristic();
        skill2.setValue(10);
        skill2.setId(2);
        dao.insertEntity(skill2);

        List<DBSkillCharacteristic> result = dao.getAllEntities();

        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getValue());
        assertEquals(10, result.get(1).getValue());
        dao.deleteEntity(skill);
        dao.deleteEntity(skill2);
    }
}