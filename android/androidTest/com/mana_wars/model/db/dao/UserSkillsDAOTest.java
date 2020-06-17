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

import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserSkillsDAOTest {

    private UserSkillsDAO dao;
    private UserSkill skill1, skill2;

    private AppDatabase db;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.userSkillsDAO();
    }

    @Before
    public void init() {
        skill1 = new UserSkill();
        skill1.setLvl(10);
        skill1.setId(1);
        skill1.setSkillID(1);

        skill2 = new UserSkill();
        skill2.setLvl(10);
        skill2.setId(2);
        skill2.setSkillID(1);

        dao.insertEntity(skill1);
        dao.insertEntity(skill2);

        DBSkill dbskill = new DBSkill();
        dbskill.setId(1);
        db.dbSkillDAO().insertEntity(dbskill);
    }

    @After
    public void clean() {
        dao.deleteEntity(skill1);
        dao.deleteEntity(skill2);
    }

    @After
    public void closeDB() {
        db.close();
    }


    @Test
    public void getEntityByID() {
        TestObserver<UserSkill> testObserver = dao.getEntityByID(1).test();

        testObserver.assertValue(us -> us.getLvl() == 10);
    }

    @Test
    public void getAllEntities() {
        TestObserver<List<UserSkill>> testObserver = dao.getAllEntities().test();

        testObserver.assertValue(l -> l.size() == 2);
        testObserver.assertValue(l -> l.get(0).getLvl() == 10);
        testObserver.assertValue(l -> l.get(1).getLvl() == 10);
    }

    @Test
    public void getUserSkills() {
        TestObserver<List<CompleteUserSkill>> testObserver = dao.getUserSkills().test();

        testObserver.assertValue(l -> l.size() == 2);
        testObserver.assertValue(l -> l.get(0).userSkill.getLvl() == 10);
        testObserver.assertValue(l -> l.get(1).userSkill.getLvl() == 10);
    }

    @Test
    public void mergeUserSkills() {
        skill1.setLvl(11);
        boolean done = dao.mergeUserSkills(skill1, skill2);

        TestObserver<UserSkill> testObserver = dao.getEntityByID(1).test();

        assertTrue(done);
        testObserver.assertValue(us -> us.getLvl() == 11);
    }
}