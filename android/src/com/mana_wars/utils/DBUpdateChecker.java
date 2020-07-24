package com.mana_wars.utils;

import android.content.Context;
import android.util.Log;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;
import com.mana_wars.model.repository.VolleyRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

public class DBUpdateChecker implements DatabaseUpdater {

    private final RoomRepository dbrepository;
    private final VolleyRepository volleyRepository;
    private final SharedPreferencesRepository preferences;
    private final Context context;

    //TEMP
    private CompositeDisposable disposable = new CompositeDisposable();

    public DBUpdateChecker(Context context,
                           RoomRepository dbrepository,
                           VolleyRepository volleyRepository,
                           SharedPreferencesRepository preferences) {
        this.dbrepository = dbrepository;
        this.context = context;
        this.preferences = preferences;
        this.volleyRepository = volleyRepository;
    }

    private Runnable callback;
    private volatile int stagesCounter = 0;

    @Override
    public void checkUpdate(Runnable callback, Runnable onError) {

        this.callback = callback;

        final int currentDBversion = preferences.getDBversion();

        if (GameConstants.DB_VERSION > currentDBversion || true) {
            try {
                new DBUpdaterParser(context).updateFromJSON(new DBUpdater(dbrepository, preferences, () -> {
                    preferences.setDBversion(GameConstants.DB_VERSION);
                    check();
                }));
            } catch (Exception e) {
                e.printStackTrace();
                onError.run();
            }
        } else check();

        //TODO extract
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        final String todayDateString = dateFormat.format(new Date());

        if (!todayDateString.equals(preferences.getLastDailySkillUpdateDate())) {

            disposable.add(volleyRepository.doGetRequest("https://arturkasymov.student.tcs.uj.edu.pl/mana_wars_daily_skills.html").subscribe(response -> {
                Log.i("response", response);

                try {
                    JSONObject responseJSON = new JSONObject(response);
                    JSONArray dailySkillsJSONArray = responseJSON.getJSONArray(todayDateString);

                    for (int i=0; i<dailySkillsJSONArray.length(); i++){
                        JSONObject dailySkillJSON = dailySkillsJSONArray.getJSONObject(i);
                        preferences.setDailySkillID(i, dailySkillJSON.getInt("skill_id"));
                        preferences.setDailySkillPrice(i, dailySkillJSON.getInt("price"));
                        preferences.setDailySkillBought(i,0);
                    }
                    preferences.setLastDailySkillUpdateDate(todayDateString);
                    check();
                }
                catch (Exception e){
                    e.printStackTrace();
                    onError.run();
                }
            }, error->{
                error.printStackTrace();
                onError.run();
            }));
        } else check();

    }

    private synchronized void check() {
        stagesCounter++;
        if (stagesCounter >= 2) {
            disposable.dispose();
            callback.run();
        }
    }
}
