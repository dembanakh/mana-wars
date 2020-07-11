package com.mana_wars.utils;

import android.content.Context;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.db.entity.DBDungeon;
import com.mana_wars.model.db.entity.DBMob;
import com.mana_wars.model.db.entity.DBMobSkill;
import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.DBSkillCharacteristic;

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
        void updateSkills(List<DBSkill> skills);
        void updateCharacteristics(List<DBSkillCharacteristic> characteristics);
        void updateDungeons(List<DBDungeon> dungeons);
        void updateMobs(List<DBMob> mobs);
        void updateMobsSkills(List<DBMobSkill> mobSkills);
    }

    void updateFromJSON(DBUpdater updater) throws IOException, JSONException {
        JSONObject dbjson = new JSONObject(readJSONStringFromFile());

        if (dbjson.getInt("version") != GameConstants.DB_VERSION)
            throw new JSONException("wrong DB version");

        updater.updateUserLvlRequiredExperience(dbjson.getJSONArray("user_lvl_required_experience").toString());

        updater.updateSkills(parseJSON(dbjson.getJSONArray("skills"), DBSkill::fromJSON));
        updater.updateCharacteristics(parseJSON(dbjson.getJSONArray("skill_characteristics"), DBSkillCharacteristic::fromJSON));
        updater.updateDungeons(parseJSON(dbjson.getJSONArray("dungeons"), DBDungeon::fromJSON));
        updater.updateMobs(parseJSON(dbjson.getJSONArray("mobs"), DBMob::fromJSON));
        updater.updateMobsSkills(parseJSON(dbjson.getJSONArray("mobs_skills"), DBMobSkill::fromJSON));
    }

    private interface JSONParser<T> {
        T fromJson(JSONObject json) throws JSONException;
    }

    // TODO think about such optimization
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
