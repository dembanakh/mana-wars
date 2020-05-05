package model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mana_wars.model.repository.LocalUserDataRepository;

public class SharedPreferencesRepository implements LocalUserDataRepository {

    private Activity hostActivity;

    //Constants
    private final String IS_FIRST_OPEN = "IS_FIRST_OPEN";

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
}
