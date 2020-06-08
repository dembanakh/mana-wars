package com.mana_wars;

import com.badlogic.gdx.Game;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.model.repository.LocalizedStringsRepository;
import com.mana_wars.ui.ScreenHandler;
import com.mana_wars.ui.ScreenManagerImpl;
import com.mana_wars.ui.screens.RepositoryStorage;

public class ManaWars extends Game implements ScreenHandler, RepositoryStorage {

	private static ManaWars instance;
	private ScreenManagerImpl screenManager;

	//platform repos
	private LocalUserDataRepository localUserDataRepository;
	private LocalizedStringsRepository localizedStringsRepository;
	private DatabaseRepository databaseRepository;

	private ManaWars() {
		screenManager = new ScreenManagerImpl(this);
	}

	public static ManaWars getInstance() {
		if (instance == null) instance = new ManaWars();
		return instance;
	}

	public ScreenManagerImpl getScreenManager() {
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

	@Override
	public LocalUserDataRepository getLocalUserDataRepository() {
		return localUserDataRepository;
	}

	public LocalizedStringsRepository getLocalizedStringsRepository() {
		return localizedStringsRepository;
	}

	public void setLocalizedStringsRepository(LocalizedStringsRepository localizedStringsRepository) {
		this.localizedStringsRepository = localizedStringsRepository;
	}

	@Override
	public DatabaseRepository getDatabaseRepository() {
		return databaseRepository;
	}

	public void setDatabaseRepository(DatabaseRepository databaseRepository) {
		this.databaseRepository = databaseRepository;
	}

}
