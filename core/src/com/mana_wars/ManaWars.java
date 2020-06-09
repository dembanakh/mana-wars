package com.mana_wars;

import com.badlogic.gdx.Game;
import com.mana_wars.model.entity.User;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.model.repository.LocalizedStringsRepository;
import com.mana_wars.ui.management.ScreenHandler;
import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.storage.UpdaterStorage;

public class ManaWars extends Game implements ScreenHandler, RepositoryStorage, UpdaterStorage {

	private static ManaWars instance;
	private final ScreenManager screenManager;
	private final User user;

	//platform repos
	private LocalUserDataRepository localUserDataRepository;
	private DatabaseRepository databaseRepository;

	private DatabaseUpdater databaseUpdater;

	private ManaWars() {
		screenManager = new ScreenManager(this);
		user = new User();
	}

	public static ManaWars getInstance() {
		if (instance == null) instance = new ManaWars();
		return instance;
	}
	
	@Override
	public void create () {
		screenManager.start();
	}
	
	@Override
	public void dispose () {
		screenManager.dispose();
	}

	public void setLocalUserDataRepository(final LocalUserDataRepository localUserDataRepository) {
		this.localUserDataRepository = localUserDataRepository;
	}

	@Override
	public LocalUserDataRepository getLocalUserDataRepository() {
		return localUserDataRepository;
	}

	@Override
	public DatabaseRepository getDatabaseRepository() {
		return databaseRepository;
	}

	public void setDatabaseRepository(final DatabaseRepository databaseRepository) {
		this.databaseRepository = databaseRepository;
	}

	public DatabaseUpdater getDatabaseUpdater() {
		return databaseUpdater;
	}

	public void setDatabaseUpdater(DatabaseUpdater databaseUpdater) {
		this.databaseUpdater = databaseUpdater;
	}
}
