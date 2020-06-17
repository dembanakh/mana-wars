package com.mana_wars.model.db.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mana_wars.model.db.AppDatabase;
import com.mana_wars.model.db.entity.DBSkillCharacteristic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.observers.TestObserver;


@RunWith(AndroidJUnit4.class)
public class DBSkillCharacteristicDAOTest {

    private DBSkillCharacteristicDAO dao;
    private AppDatabase db;
    private DBSkillCharacteristic characteristic1, characteristic2;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.dbSkillCharacteristicDAO();

    }

    @Before
    public void init() {
        characteristic1 = new DBSkillCharacteristic();
        characteristic1.setValue(10);
        characteristic1.setId(1);

        characteristic2 = new DBSkillCharacteristic();
        characteristic2.setValue(10);
        characteristic2.setId(2);

        dao.insertEntity(characteristic1);
        dao.insertEntity(characteristic2);
    }

    @After
    public void clean() {
        dao.deleteEntity(characteristic1);
        dao.deleteEntity(characteristic2);
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void getEntityByID() {

        TestObserver<DBSkillCharacteristic> testObserver = dao.getEntityByID(1).test();

        testObserver.assertValue(s -> s.getValue() == 10);
    }

    @Test
    public void getAllEntities() {
        TestObserver<List<DBSkillCharacteristic>> testObserver = dao.getAllEntities().test();

        testObserver.assertValue(l -> l.size() == 2);
        testObserver.assertValue(l -> l.get(0).getValue() == 10);
        testObserver.assertValue(l -> l.get(1).getValue() == 10);
    }
}