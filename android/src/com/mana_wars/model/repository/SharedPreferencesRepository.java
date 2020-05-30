package com.mana_wars.model.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesRepository implements LocalUserDataRepository {

    private Activity hostActivity;

    //Constants
    private final String DB_VERSION = "DB_VERSION";
    private final String USERNAME = "USERNAME";

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
}
