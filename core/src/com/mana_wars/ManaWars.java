package com.mana_wars;

import com.badlogic.gdx.Game;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.model.repository.LocalizedStringsRepository;
import com.mana_wars.ui.screens.ScreenHandler;
import com.mana_wars.ui.screens.ScreenManager;

public class ManaWars extends Game implements ScreenHandler {

	private static ManaWars instance;
	private ScreenManager screenManager;

	//platform repos
	private LocalUserDataRepository localUserDataRepository;
	private LocalizedStringsRepository localizedStringsRepository;
	private DatabaseRepository databaseRepository;

	private ManaWars() {
		screenManager = new ScreenManager(this);
	}

	public static ManaWars getInstance() {
		if (instance == null) instance = new ManaWars();
		return instance;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}
	
	@Override
	public void create () {
		screenManager.start();
	}
	
	@Override
	public void dispose () {
		screenManager.dispose();
	}

	public void setLocalUserDataRepository(LocalUserDataRepository localUserDataRepository) {
		this.localUserDataRepository = localUserDataRepository;
	}

	public LocalUserDataRepository getLocalUserDataRepository() {
		return localUserDataRepository;
	}

	public LocalizedStringsRepository getLocalizedStringsRepository() {
		return localizedStringsRepository;
	}

	public void setLocalizedStringsRepository(LocalizedStringsRepository localizedStringsRepository) {
		this.localizedStringsRepository = localizedStringsRepository;
	}

	public DatabaseRepository getDatabaseRepository() {
		return databaseRepository;
	}

	public void setDatabaseRepository(DatabaseRepository databaseRepository) {
		this.databaseRepository = databaseRepository;
	}
}
