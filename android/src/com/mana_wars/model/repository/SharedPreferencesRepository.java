package com.mana_wars.model.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesRepository implements LocalUserDataRepository {

    private final Activity hostActivity;

    //Constants
    private final String DB_VERSION = "DB_VERSION";
    private final String USERNAME = "USERNAME";
    private final String USER_LEVEL = "USER_LEVEL";
    private final String USER_MANA = "USER_MANA";
    private final String LAST_MANA_BONUS_TIME = "LAST_MANA_BONUS_TIME";
    private final String USER_LVL_REQUIRED_EXPERIENCE = "USER_LVL_REQUIRED_EXPERIENCE";
    private final String CURRENT_USER_EXPERIENCE = "CURRENT_USER_EXPERIENCE";
    private final String USER_SKILL_CASES = "USER_SKILL_CASES";

    private final String LAST_DAILY_SKILL_UPDATE_DATE = "LAST_DAILY_SKILL_UPDATE_DATE";
    private final String DAILY_SKILL_BOUGHT = "DAILY_SKILL_BOUGHT";
    private final String DAILY_SKILL_ID = "DAILY_SKILL_ID";
    private final String DAILY_SKILL_PRICE = "DAILY_SKILL_PRICE";


    public SharedPreferencesRepository(Activity hostActivity) {
        this.hostActivity = hostActivity;
    }

    private SharedPreferences.Editor getPrefsEditor() {
        return hostActivity.getPreferences(Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences getDefaultManager() {
        return hostActivity.getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public int getDBversion() {
        return getDefaultManager().getInt(DB_VERSION, 0);
    }

    @Override
    public void setDBversion(int version) {
        getPrefsEditor().putInt(DB_VERSION, version).apply();
    }

    @Override
    public String getUsername() {
        return getDefaultManager().getString(USERNAME, null);
    }

    @Override
    public void setUsername(String username) {
        getPrefsEditor().putString(USERNAME, username).apply();
    }

    @Override
    public int getUserLevel() {
        return getDefaultManager().getInt(USER_LEVEL, 0);
    }

    @Override
    public void setUserLevel(int level) {
        getPrefsEditor().putInt(USER_LEVEL, level).apply();
    }

    @Override
    public int getCurrentUserExperience() {
        return getDefaultManager().getInt(CURRENT_USER_EXPERIENCE, 0);
    }

    @Override
    public void setCurrentUserExperience(int currentUserExperience) {
        getPrefsEditor().putInt(CURRENT_USER_EXPERIENCE, currentUserExperience).apply();
    }

    @Override
    public int getUserMana() {
        return getDefaultManager().getInt(USER_MANA, 0);
    }

    @Override
    public void setUserMana(int mana) {
        getPrefsEditor().putInt(USER_MANA, mana).apply();
    }

    @Override
    public boolean wasBonusEverClaimed() {
        return getLastTimeBonusClaimed() != 0;
    }

    @Override
    public long getLastTimeBonusClaimed() {
        return getDefaultManager().getLong(LAST_MANA_BONUS_TIME, 0);
    }

    @Override
    public void setLastTimeBonusClaimed(long time) {
        getPrefsEditor().putLong(LAST_MANA_BONUS_TIME, time).apply();
    }

    public void setUserLvlRequiredExperience(String userLvlRequiredExperience) {
        getPrefsEditor().putString(USER_LVL_REQUIRED_EXPERIENCE, userLvlRequiredExperience).apply();
    }

    @Override
    public List<Integer> getUserLevelRequiredExperience() {

        List<Integer> userLvlReq = new ArrayList<>();

        String jsonArrayString = getDefaultManager().getString(USER_LVL_REQUIRED_EXPERIENCE, "[0]");
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            for (int i = 0; i < jsonArray.length(); i++) {
                userLvlReq.add(jsonArray.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userLvlReq;
    }

    @Override
    public int getSkillCasesNumber() {
        return getDefaultManager().getInt(USER_SKILL_CASES, 0);
    }

    @Override
    public void updateSkillCasesNumber(int delta) {
        getPrefsEditor().putInt(USER_SKILL_CASES, getSkillCasesNumber() + delta).apply();
    }

    public void setDailySkillBought(int skill, int amount) {
        getPrefsEditor().putInt(DAILY_SKILL_BOUGHT + skill, amount).apply();
    }

    int getDailySkillBought(int skill) {
        return getDefaultManager().getInt(DAILY_SKILL_BOUGHT + skill, 0);
    }

    public void setDailySkillID(int skill, int id) {
        getPrefsEditor().putInt(DAILY_SKILL_ID + skill, id).apply();
    }

    int getDailySkillID(int skill) {
        return getDefaultManager().getInt(DAILY_SKILL_ID + skill, -1);
    }

    public void setDailySkillPrice(int skill, int price) {
        getPrefsEditor().putInt(DAILY_SKILL_PRICE + skill, price).apply();
    }

    int getDailySkillPrice(int skill) {
        return getDefaultManager().getInt(DAILY_SKILL_PRICE + skill, 0);
    }

    public void setLastDailySkillUpdateDate(String s) {
        getPrefsEditor().putString(LAST_DAILY_SKILL_UPDATE_DATE, s).apply();
    }

    public String getLastDailySkillUpdateDate() {
        return getDefaultManager().getString(LAST_DAILY_SKILL_UPDATE_DATE, "2000/07/05");
    }
}
