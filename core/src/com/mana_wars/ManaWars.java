package com.mana_wars;

import com.badlogic.gdx.Game;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.model.repository.LocalizedStringsRepository;
import com.mana_wars.ui.FirstOpenFlag;
import com.mana_wars.ui.ScreenHandler;
import com.mana_wars.ui.ScreenManagerImpl;

public class ManaWars extends Game implements ScreenHandler {

	private static ManaWars instance;
	private ScreenManagerImpl screenManager;

	//platform repos
	private LocalUserDataRepository localUserDataRepository;
	private LocalizedStringsRepository localizedStringsRepository;
	private DatabaseRepository databaseRepository;
	private FirstOpenFlag firstOpenFlag;

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
		screenManager.start(firstOpenFlag);
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

	public void setFirstOpenFlag(FirstOpenFlag firstOpenFlag) {
		this.firstOpenFlag = firstOpenFlag;
	}

}
