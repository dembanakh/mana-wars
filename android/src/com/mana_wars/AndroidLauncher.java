package com.mana_wars;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import com.mana_wars.model.repository.DBMapperRepository;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;
import com.mana_wars.utils.DBUpdateChecker;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		config.useAccelerometer = false;
		config.useGyroscope = false;

		ManaWars app = ManaWars.getInstance();

		SharedPreferencesRepository sharedPreferencesRepository = new SharedPreferencesRepository(this);
		RoomRepository roomRepository = RoomRepository.getInstance(this);


		app.setLocalUserDataRepository(sharedPreferencesRepository);
		app.setDatabaseRepository(new DBMapperRepository(roomRepository));
		app.setDatabaseUpdater(new DBUpdateChecker(this, roomRepository, sharedPreferencesRepository));


		initialize(app, config);
	}
}
