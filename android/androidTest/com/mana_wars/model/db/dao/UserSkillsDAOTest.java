package com.mana_wars.model.db.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mana_wars.model.db.AppDatabase;
import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.UserSkill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserSkillsDAOTest {

    UserSkillsDAO dao;
    private AppDatabase db;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.userSkillsDAO();

    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void getEntityByID() {
        UserSkill skill = new UserSkill();
        skill.setLvl(10);
        skill.setId(1);
        dao.insertEntity(skill);

        UserSkill result = dao.getEntityByID(1);

        assertEquals(10, result.getLvl());
        dao.deleteEntity(skill);
    }

    @Test
    public void getAllEntities() {
        UserSkill skill = new UserSkill();
        skill.setLvl(10);
        skill.setId(1);
        dao.insertEntity(skill);

        UserSkill skill2 = new UserSkill();
        skill2.setLvl(10);
        skill2.setId(2);
        dao.insertEntity(skill2);


        List<UserSkill> result = dao.getAllEntities();

        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getLvl());
        assertEquals(10, result.get(1).getLvl());
        dao.deleteEntity(skill);
        dao.deleteEntity(skill2);
    }

    @Test
    public void getUserSkills() {

        UserSkill skill = new UserSkill();
        skill.setLvl(10);
        skill.setId(1);
        skill.setSkillID(1);
        dao.insertEntity(skill);

        UserSkill skill2 = new UserSkill();
        skill2.setLvl(10);
        skill2.setId(2);
        skill2.setSkillID(1);
        dao.insertEntity(skill2);

        DBSkill dbskill = new DBSkill();
        dbskill.setId(1);
        db.dbSkillDAO().insertEntity(dbskill);


        List<CompleteUserSkill> result = dao.getUserSkills();

        assertEquals(2, result.size());
        assertEquals(10, result.get(0).userSkill.getLvl());
        assertEquals(10, result.get(1).userSkill.getLvl());
        dao.deleteEntity(skill);
        dao.deleteEntity(skill2);
    }

    @Test
    public void mergeUserSkills() {

        UserSkill skill = new UserSkill();
        skill.setLvl(10);
        skill.setId(1);
        dao.insertEntity(skill);

        UserSkill skill2 = new UserSkill();
        skill2.setLvl(10);
        skill2.setId(2);
        dao.insertEntity(skill2);


        skill.setLvl(11);
        boolean done = dao.mergeUserSkills(skill, skill2);
        UserSkill result = dao.getEntityByID(1);

        assertTrue(done);
        assertEquals(11, result.getLvl());
        dao.deleteEntity(skill);

    }
}