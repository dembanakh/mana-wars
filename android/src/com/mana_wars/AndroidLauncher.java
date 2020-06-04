package com.mana_wars;

<<<<<<< HEAD

=======
>>>>>>> 074c49d... further Battle refactor
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import com.mana_wars.model.repository.DBMapperRepository;
import com.mana_wars.model.repository.RoomRepository;
import com.mana_wars.model.repository.SharedPreferencesRepository;
import com.mana_wars.utils.DBUpdateChecker;


import static com.mana_wars.model.repository.LocalizedStringsRepository.SKILL_LABEL;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;

		config.useAccelerometer = false;
		config.useGyroscope = false;


		ManaWars app = ManaWars.getInstance();

		//todo replace
		app.setLocalizedStringsRepository(id->
		{
			switch (id) {
				case SKILL_LABEL:
					return getResources().getString(R.string.skills_label);

				default:
					return null;
			}
		});

		SharedPreferencesRepository sharedPreferencesRepository = new SharedPreferencesRepository(this);
		RoomRepository roomRepository = RoomRepository.getInstance(this);


		app.setLocalUserDataRepository(sharedPreferencesRepository);

		app.setDatabaseRepository(new DBMapperRepository(roomRepository));

		//TODO refactor
		DBUpdateChecker.check(this, sharedPreferencesRepository, roomRepository);

		initialize(app, config);

	}


}
