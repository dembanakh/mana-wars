package com.mana_wars.utils;

import com.mana_wars.model.repository.SharedPreferencesRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.mana_wars.model.GameConstants.DAILY_SKILLS_COUNT;

public class DailySkillsOfferParser {

    static synchronized void updateFromJSON(final SharedPreferencesRepository preferences, final String jsonString, final String todayDateString) throws JSONException {
        JSONObject responseJSON = new JSONObject(jsonString);
        JSONArray dailySkillsJSONArray = responseJSON.getJSONArray(todayDateString);
        for (int i = 0; i < DAILY_SKILLS_COUNT; i++) {
            JSONObject dailySkillJSON = dailySkillsJSONArray.getJSONObject(i);
            preferences.setDailySkillID(i, dailySkillJSON.getInt("skill_id"));
            preferences.setDailySkillPrice(i, dailySkillJSON.getInt("price"));
            preferences.setDailySkillBought(i, 0);
        }
        preferences.setLastDailySkillUpdateDate(todayDateString);
    }

    private DailySkillsOfferParser() {}
}
