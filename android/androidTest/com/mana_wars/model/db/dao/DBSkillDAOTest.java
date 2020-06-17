package com.mana_wars.model.db.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mana_wars.model.db.AppDatabase;
import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DBSkillDAOTest {

    DBSkillDAO dao;
    private AppDatabase db;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.dbSkillDAO();

    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void getEntityByID() {
        DBSkill skill = new DBSkill();
        skill.setName("aaa");
        skill.setId(1);
        dao.insertEntity(skill);

        DBSkill result = dao.getEntityByID(1);

        assertEquals("aaa", result.getName());
        dao.deleteEntity(skill);
    }

    @Test
    public void getAllEntities() {
        DBSkill skill = new DBSkill();
        skill.setName("aaa");
        skill.setId(1);
        dao.insertEntity(skill);


        DBSkill skill2 = new DBSkill();
        skill2.setName("bbb");
        skill2.setId(2);
        dao.insertEntity(skill2);

        List<DBSkill> result = dao.getAllEntities();

        assertEquals(2, result.size());
        assertTrue(result.get(0).getName().equals("aaa") || result.get(1).getName().equals("aaa"));
        assertTrue(result.get(0).getName().equals("bbb") || result.get(1).getName().equals("bbb"));
        dao.deleteEntity(skill);
        dao.deleteEntity(skill2);
    }

    @Test
    public void getSkillsWithCharacteristics() {

        DBSkill skill = new DBSkill();
        skill.setName("aaa");
        skill.setId(1);
        dao.insertEntity(skill);


        DBSkill skill2 = new DBSkill();
        skill2.setName("bbb");
        skill2.setId(2);
        dao.insertEntity(skill2);

        List<DBSkillWithCharacteristics> result = dao.getSkillsWithCharacteristics();

        assertEquals(2, result.size());
        assertTrue(result.get(0).skill.getName().equals("aaa") || result.get(1).skill.getName().equals("aaa"));
        assertTrue(result.get(0).skill.getName().equals("bbb") || result.get(1).skill.getName().equals("bbb"));
        dao.deleteEntity(skill);
        dao.deleteEntity(skill2);
    }
}