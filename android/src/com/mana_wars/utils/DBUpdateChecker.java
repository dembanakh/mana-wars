package com.mana_wars.utils;

import android.content.Context;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;

public class DBUpdateChecker implements DatabaseUpdater {

    private final RoomRepository dbrepository;
    private final Context context;
    private final SharedPreferencesRepository preferences;

    public DBUpdateChecker(Context context, RoomRepository dbrepository,
                           SharedPreferencesRepository preferences) {
        this.dbrepository = dbrepository;
        this.context = context;
        this.preferences = preferences;
    }

    @Override
    public void checkUpdate(Runnable callback) {

        final int currentDBversion = preferences.getDBversion();

        if (GameConstants.DB_VERSION > currentDBversion || true) {
            try {
                new DBUpdaterParser(context).updateFromJSON(new DBUpdater(dbrepository, preferences, () -> {
                    preferences.setDBversion(GameConstants.DB_VERSION);
                    callback.run();
                }));
            } catch (Exception e) {
                //TODO ask about delete and download app again
                e.printStackTrace();
            }
        } else callback.run();
    }
}
