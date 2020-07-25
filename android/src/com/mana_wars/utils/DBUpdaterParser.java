package com.mana_wars.utils;

import android.content.Context;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.db.dao.BaseDAO;
import com.mana_wars.model.db.entity.base.DBDungeon;
import com.mana_wars.model.db.entity.base.DBDungeonRoundDescription;
import com.mana_wars.model.db.entity.base.DBMob;
import com.mana_wars.model.db.entity.base.DBMobSkill;
import com.mana_wars.model.db.entity.base.DBSkill;
import com.mana_wars.model.db.entity.base.DBSkillCharacteristic;
import com.mana_wars.model.repository.RoomRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DBUpdaterParser {

    public final Context context;

    DBUpdaterParser(Context context) {
        this.context = context;
    }

    public interface DBUpdater {
        void updateUserLvlRequiredExperience(String userLvlRequiredExperience);
        <T> void update(List<T> entities, DAOMapper<T> DAOMapper);
    }

    public interface DAOMapper<V> {
        BaseDAO<V> get(RoomRepository repository);
    }

    void updateFromJSON(DBUpdater updater) throws IOException, JSONException {
        JSONObject dbjson = new JSONObject(readJSONStringFromFile());

        if (dbjson.getInt("version") != GameConstants.DB_VERSION)
            throw new JSONException("wrong DB version");

        updater.updateUserLvlRequiredExperience(dbjson.getJSONArray("user_lvl_required_experience").toString());

        updater.update(parseJSON(dbjson.getJSONArray("skills"), DBSkill::fromJSON), repository -> repository.dbSkillDAO);
        updater.update(parseJSON(dbjson.getJSONArray("skill_characteristics"), DBSkillCharacteristic::fromJSON), repository -> repository.dbSkillCharacteristicDAO);
        updater.update(parseJSON(dbjson.getJSONArray("dungeons"), DBDungeon::fromJSON), repository -> repository.dbDungeonDAO);
        updater.update(parseJSON(dbjson.getJSONArray("mobs"), DBMob::fromJSON), repository -> repository.dbMobDAO);
        updater.update(parseJSON(dbjson.getJSONArray("mobs_skills"), DBMobSkill::fromJSON), repository -> repository.dbMobSkillDAO);
        updater.update(parseJSON(dbjson.getJSONArray("dungeon_rounds_description"), DBDungeonRoundDescription::fromJSON), repository -> repository.dbDungeonRoundDescriptionDAO);
    }

    private interface JSONParser<T> {
        T fromJson(JSONObject json) throws JSONException;
    }

    private <T> List<T> parseJSON(JSONArray jsonArray, JSONParser<T> parser) throws JSONException {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(parser.fromJson(jsonArray.getJSONObject(i)));
        }
        return list;
    }


    private String readJSONStringFromFile() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            String jsonDataString;
            inputStream = context.getAssets().open("DB.json");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return builder.toString();
    }

}
