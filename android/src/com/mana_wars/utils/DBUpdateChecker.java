package com.mana_wars.utils;

import android.content.Context;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;

public class DBUpdateChecker {


    //TODO handle situation when update failed and DBversion is 0
    public static void check(Context context, SharedPreferencesRepository preferences, RoomRepository dbrepository){

        final int currentDBversion = preferences.getDBversion();

        if (GameConstants.DB_VERSION > currentDBversion){
            try {
                new DBUpdaterParser(context).updateFromJSON(new DBUpdater(dbrepository, ()->{
                    preferences.setDBversion(GameConstants.DB_VERSION);
                }));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
