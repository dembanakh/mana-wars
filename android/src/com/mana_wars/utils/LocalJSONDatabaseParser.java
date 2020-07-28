package com.mana_wars.utils;

import android.content.Context;

import com.mana_wars.model.db.entity.base.DBDungeon;
import com.mana_wars.model.db.entity.base.DBDungeonRoundDescription;
import com.mana_wars.model.db.entity.base.DBMob;
import com.mana_wars.model.db.entity.base.DBMobSkill;
import com.mana_wars.model.db.entity.base.DBSkill;
import com.mana_wars.model.db.entity.base.DBSkillCharacteristic;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;

import static com.mana_wars.utils.ApplicationConstants.DB_VERSION;

class LocalJSONDatabaseParser {

    private LocalJSONDatabaseParser() {}

    static synchronized Completable updateFromJSON(final Context context, final SharedPreferencesRepository preferences, RoomRepository repository) throws IOException, JSONException {
        return new LocalJSONDatabaseParser().updateFromLocalJSON(context, preferences, repository);
    }

    private Completable updateFromLocalJSON(final Context context, final SharedPreferencesRepository preferences, final RoomRepository repository) throws IOException, JSONException {
        JSONObject dbjson = new JSONObject(readJSONStringFromFile(context));

        if (dbjson.getInt("version") != DB_VERSION)
            throw new JSONException("wrong DB version");

        preferences.setUserLvlRequiredExperience(dbjson.getJSONArray("user_lvl_required_experience").toString());

        return repository.insertEntities(parseJSON(dbjson.getJSONArray("skills"), DBSkill::fromJSON), repository.dbSkillDAO)
                .mergeWith(
                        repository.insertEntities(parseJSON(dbjson.getJSONArray("skill_characteristics"), DBSkillCharacteristic::fromJSON), repository.dbSkillCharacteristicDAO))
                .mergeWith(
                        repository.insertEntities(parseJSON(dbjson.getJSONArray("dungeons"), DBDungeon::fromJSON), repository.dbDungeonDAO))
                .mergeWith(
                        repository.insertEntities(parseJSON(dbjson.getJSONArray("mobs"), DBMob::fromJSON), repository.dbMobDAO))
                .mergeWith(
                        repository.insertEntities(parseJSON(dbjson.getJSONArray("mobs_skills"), DBMobSkill::fromJSON), repository.dbMobSkillDAO))
                .mergeWith(
                        repository.insertEntities(parseJSON(dbjson.getJSONArray("dungeon_rounds_description"), DBDungeonRoundDescription::fromJSON), repository.dbDungeonRoundDescriptionDAO)
                );
    }

    private <T> List<T> parseJSON(JSONArray jsonArray, JSONParser<T> parser) throws JSONException {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(parser.fromJson(jsonArray.getJSONObject(i)));
        }
        return list;
    }

    private String readJSONStringFromFile(Context context) throws IOException {
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

    private interface JSONParser<T> {
        T fromJson(JSONObject json) throws JSONException;
    }

}
