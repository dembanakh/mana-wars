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
    }

    public void updateFromJSON(DBUpdater updater) throws IOException, JSONException {
        JSONObject dbjson = new JSONObject(readJSONStringFromFile());

        if(dbjson.getInt("version")!= GameConstants.DB_VERSION) throw new JSONException("wrong DB version");

        JSONArray skillsJSON = dbjson.getJSONArray("skills");
        List<DBSkill> skills = new ArrayList<>();
        for(int i=0; i<skillsJSON.length();i++){
            skills.add(DBSkill.fromJSON(skillsJSON.getJSONObject(i)));
        }
        updater.insertSkills(skills);

        JSONArray characteristicsJSON = dbjson.getJSONArray("skill_characteristics");
        List<DBSkillCharacteristic> characteristics = new ArrayList<>();
        for(int i=0; i<characteristicsJSON.length();i++){
            characteristics.add(DBSkillCharacteristic.fromJSON(characteristicsJSON.getJSONObject(i)));
        }
        updater.insertCharacteristics(characteristics);
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
