package com.mana_wars.model.db.dao;

import android.content.Context;

import androidx.room.Room;


import com.mana_wars.model.db.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DBSkillDAOTest {

    private AppDatabase db;
    DBSkillDAO dao;
    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.dbSkillDAO();
    }

    @After
    public void closeDB(){
        db.close();
    }

    @Test
    public void getEntityByID() {

        assertTrue(true);
    }

    @Test
    public void getAllEntities() {

    }

    @Test
    public void getSkillsWithCharacteristics() {
    }
}