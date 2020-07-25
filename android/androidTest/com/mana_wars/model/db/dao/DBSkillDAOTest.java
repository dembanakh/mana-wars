package com.mana_wars.model.db.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mana_wars.model.db.AppDatabase;
import com.mana_wars.model.db.entity.base.DBSkill;
import com.mana_wars.model.db.entity.query.DBSkillWithCharacteristics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class DBSkillDAOTest {

    private DBSkillDAO dao;
    private AppDatabase db;

    private DBSkill skill1, skill2;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.dbSkillDAO();
    }

    @Before
    public void init() {
        skill1 = new DBSkill();
        skill1.setName("aaa");
        skill1.setId(1);
        dao.insertEntity(skill1);

        skill2 = new DBSkill();
        skill2.setName("bbb");
        skill2.setId(2);
        dao.insertEntity(skill2);
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
        TestObserver<DBSkill> testObserver = dao.getEntityByID(1).test();

        testObserver.assertValue(s -> s.getName().equals("aaa"));
    }

    @Test
    public void getAllEntities() {
        TestObserver<List<DBSkill>> testObserver = dao.getAllEntities().test();

        testObserver.assertValue(l -> l.size() == 2);
        testObserver.assertValue(s -> s.get(0).getName().equals("aaa") || s.get(1).getName().equals("aaa"));
        testObserver.assertValue(s -> s.get(0).getName().equals("bbb") || s.get(1).getName().equals("bbb"));
    }

    @Test
    public void getSkillsWithCharacteristics() {
        TestObserver<List<DBSkillWithCharacteristics>> testObserver = dao.getSkillsWithCharacteristics().test();

        testObserver.assertValue(l -> l.size() == 2);
        testObserver.assertValue(s -> s.get(0).skill.getName().equals("aaa") || s.get(1).skill.getName().equals("aaa"));
        testObserver.assertValue(s -> s.get(0).skill.getName().equals("bbb") || s.get(1).skill.getName().equals("bbb"));
    }
}