package com.mana_wars.utils;

import android.content.Context;

import com.mana_wars.model.repository.ApplicationDataUpdater;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;
import com.mana_wars.model.repository.VolleyRepository;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

import static com.mana_wars.utils.ApplicationConstants.DAILY_SKILLS_JSON_URL;
import static com.mana_wars.utils.ApplicationConstants.DB_VERSION;
import static com.mana_wars.utils.ApplicationConstants.DEFAULT_APPLICATION_DATE_FORMAT;

public class UpdateChecker implements ApplicationDataUpdater {

    private final RoomRepository dbrepository;
    private final VolleyRepository volleyRepository;
    private final SharedPreferencesRepository preferences;
    private final Context context;

    private CompositeDisposable disposable;
    private CompletableSubject cs;

    private volatile int stagesCounter;
    private volatile int stagesLimit;

    public UpdateChecker(Context context,
                         RoomRepository dbrepository,
                         VolleyRepository volleyRepository,
                         SharedPreferencesRepository preferences) {
        this.dbrepository = dbrepository;
        this.context = context;
        this.preferences = preferences;
        this.volleyRepository = volleyRepository;
    }

    @Override
    public synchronized Completable checkFullUpdate() {
        reInit(2);
        checkLocalJSON();
        checkDailySkillsOfferJSON();
        return cs.observeOn(Schedulers.io());
    }

    public synchronized Completable checkDailySkillsOffer() {
        reInit(1);
        checkDailySkillsOfferJSON();
        return cs.observeOn(Schedulers.io());
    }

    private void checkLocalJSON() {
        final int currentDBversion = preferences.getDBversion();
        if (DB_VERSION > currentDBversion || true) {
            try {
                disposable.add(
                        LocalJSONDatabaseParser.updateFromJSON(context, preferences, dbrepository).subscribe(
                                () -> {
                                    preferences.setDBversion(DB_VERSION);
                                    check();
                                },
                                this::callError
                        )
                );
            } catch (Exception e) {
                callError(e);
            }
        } else check();
    }

    private void checkDailySkillsOfferJSON() {
        final String todayDateString = DEFAULT_APPLICATION_DATE_FORMAT.format(new Date());
        if (!todayDateString.equals(preferences.getLastDailySkillUpdateDate()) || true) {
            disposable.add(volleyRepository.doGetRequest(DAILY_SKILLS_JSON_URL).subscribe(response -> {
                try {
                    DailySkillsOfferParser.updateFromJSON(preferences, response, todayDateString);
                    check();
                } catch (Exception e) {
                    callError(e);
                }
            }, this::callError));
        } else check();
    }

    private synchronized void check() {
        stagesCounter++;
        if (stagesCounter >= stagesLimit) {
            disposable.dispose();
            cs.onComplete();
        }
    }

    private synchronized void callError(Throwable e) {
        cs.onError(e);
    }

    private void reInit(int stagesLimit) {
        this.cs = CompletableSubject.create();
        this.disposable = new CompositeDisposable();
        this.stagesCounter = 0;
        this.stagesLimit = stagesLimit;
    }

}
