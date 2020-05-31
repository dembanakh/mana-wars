package com.mana_wars.model.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesRepository implements LocalUserDataRepository {

    private Activity hostActivity;

    //Constants
    private final String DB_VERSION = "DB_VERSION";
    private final String USERNAME = "USERNAME";
    private final String USER_LEVEL = "USER_LEVEL";
    private final String USER_MANA = "USER_MANA";
    private final String LAST_MANA_BONUS_TIME = "LAST_MANA_BONUS_TIME";

    public SharedPreferencesRepository(Activity hostActivity){
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
        return getDefaultManager().getInt(DB_VERSION,0);
    }

    @Override
    public void setDBversion(int version) {
        getPrefsEditor().putInt(DB_VERSION, version).apply();
    }

    @Override
    public boolean hasUsername() {
        return getUsername() != null;
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
        return getDefaultManager().getInt(USER_LEVEL, 1);
    }

    @Override
    public void setUserLevel(int level) {
        getPrefsEditor().putInt(USER_LEVEL, level).apply();
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
}
