package com.mana_wars;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mana_wars.model.repository.DBMapperRepository;
import com.mana_wars.model.repository.DailySkillsRepository;
import com.mana_wars.model.repository.DailySkillsRepositoryImpl;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;
import com.mana_wars.model.repository.VolleyRepository;
import com.mana_wars.utils.UpdateChecker;

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
        UpdateChecker updateChecker = new UpdateChecker(this, roomRepository, volleyRepository, sharedPreferencesRepository);
        DailySkillsRepository dailySkillsRepository = new DailySkillsRepositoryImpl(roomRepository, sharedPreferencesRepository, updateChecker);

        initialize(new ManaWars(sharedPreferencesRepository,
                        new DBMapperRepository(roomRepository),
                        dailySkillsRepository, updateChecker
                        ),
                config);
    }

    @Override
    public void onBackPressed() {

    }
}
