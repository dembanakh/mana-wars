package com.mana_wars;

import android.app.Application;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import model.SharedPreferencesRepository;

import static com.mana_wars.ui.LocalizedStringsRepository.SKILL_LABEL;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;

		config.useAccelerometer = false;
		config.useGyroscope = false;


		ManaWars app = ManaWars.getInstance();
		//TODO set android impl classes
		app.setLocalUserDataRepository(new SharedPreferencesRepository(this));
		app.setLocalizedStringsRepository(id->
		{
			switch (id) {
				case SKILL_LABEL:
					return getResources().getString(R.string.skills_label);

				default:
					return null;
			}
		});
		initialize(app, config);

	}
}
