package com.mana_wars;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mana_wars.model.repository.DBMapperRepository;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;
import com.mana_wars.model.repository.VolleyRepository;
import com.mana_wars.utils.DBUpdateChecker;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useAccelerometer = false;
        config.useGyroscope = false;
        config.useImmersiveMode = true;

        SharedPreferencesRepository sharedPreferencesRepository = new SharedPreferencesRepository(this);
        RoomRepository roomRepository = RoomRepository.getInstance(this);
        VolleyRepository volleyRepository = VolleyRepository.getInstance(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(
                task -> {
                    if (!task.isSuccessful()) {
                        Log.i("FCM id", "getInstanceId failed", task.getException());
                        return;
                    }
                    String token = task.getResult().getToken();
                    Log.d("FCM id", token);
                    volleyRepository.postFCMUserTokenToServer(token);
                }
        );

        initialize(new ManaWars(sharedPreferencesRepository,
                        new DBMapperRepository(roomRepository),
                        new DBUpdateChecker(this, roomRepository, sharedPreferencesRepository)),
                config);
    }

    @Override
    public void onBackPressed() {
        // TODO: NOT handle
    }
}
