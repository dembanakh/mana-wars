package com.mana_wars.model.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mana_wars.ui.FirstOpenFlag;

public class SharedPreferencesRepository implements LocalUserDataRepository, FirstOpenFlag {

    private Activity hostActivity;

    //Constants
    private final String IS_FIRST_OPEN = "IS_FIRST_OPEN";
    private final String DB_VERSION = "DB_VERSION";

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
    public boolean getIsFirstOpen() {
        return getDefaultManager().getBoolean(IS_FIRST_OPEN, true);
    }

    @Override
    public void setIsFirstOpen(boolean flag) {
        getPrefsEditor().putBoolean(IS_FIRST_OPEN, flag).apply();
    }

    @Override
    public int getDBversion() {
        return getDefaultManager().getInt(DB_VERSION,0);
    }

    @Override
    public void setDBversion(int version) {
        getPrefsEditor().putInt(DB_VERSION, version).apply();
    }
}
