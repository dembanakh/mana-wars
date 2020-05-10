package com.mana_wars;

import com.badlogic.gdx.Game;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.ui.LocalizedStringsRepository;
import com.mana_wars.ui.screens.ScreenHandler;
import com.mana_wars.ui.screens.ScreenManager;

public class ManaWars extends Game implements ScreenHandler {

	private static ManaWars instance;
	private ScreenManager screenManager;

	//platform repos
	private LocalUserDataRepository localUserDataRepository;
	private LocalizedStringsRepository localizedStringsRepository;

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
}
