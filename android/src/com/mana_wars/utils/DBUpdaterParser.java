package com.mana_wars.utils;

import android.content.Context;

import com.mana_wars.model.GameConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.mana_wars.model.db.entity.DBDungeon;
import com.mana_wars.model.db.entity.DBMob;
import com.mana_wars.model.db.entity.DBMobSkill;
import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.DBSkillCharacteristic;

public class DBUpdaterParser {

    public Context context;

    public DBUpdaterParser(Context context){
        this.context = context;
    }

    public interface DBUpdater{
        void insertSkills(List<DBSkill> skills);
        void insertCharacteristics(List<DBSkillCharacteristic> characteristics);
        void insertDungeons(List<DBDungeon> dungeons);
        void insertMobs(List<DBMob> mobs);
        void insertMobsSkills(List<DBMobSkill> mobSkills);
    }

    public void updateFromJSON(DBUpdater updater) throws IOException, JSONException {
        JSONObject dbjson = new JSONObject(readJSONStringFromFile());

        if(dbjson.getInt("version")!= GameConstants.DB_VERSION) throw new JSONException("wrong DB version");

        updater.insertSkills(parseJSON(dbjson.getJSONArray("skills"), DBSkill::fromJSON));
        updater.insertCharacteristics(parseJSON(dbjson.getJSONArray("skill_characteristics"), DBSkillCharacteristic::fromJSON));
        updater.insertDungeons(parseJSON(dbjson.getJSONArray("dungeons"), DBDungeon::fromJSON));
        updater.insertMobs(parseJSON(dbjson.getJSONArray("mobs"), DBMob::fromJSON));
        updater.insertMobsSkills(parseJSON(dbjson.getJSONArray("mobs_skills"), DBMobSkill::fromJSON));
    }

    //TODO rename
    private interface CreatorFromJson<T>{
        T fromJson(JSONObject json) throws JSONException;
    }

    // TODO think about such an optimization
    private <T> List<T> parseJSON(JSONArray jsonArray, CreatorFromJson<T> creator) throws JSONException {
        List<T> list = new ArrayList<>();

        for(int i=0; i<jsonArray.length();i++){
            list.add(creator.fromJson(jsonArray.getJSONObject(i)));
        }
        return list;
    }


    private String readJSONStringFromFile() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            String jsonDataString = null;
            inputStream=context.getAssets().open("DB.json");
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
