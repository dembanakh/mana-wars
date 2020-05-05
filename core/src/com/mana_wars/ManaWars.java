package com.mana_wars;

import com.badlogic.gdx.Game;
import com.mana_wars.view.screens.ScreenHandler;
import com.mana_wars.view.screens.ScreenManager;

public class ManaWars extends Game implements ScreenHandler {

	private static ManaWars instance;

	private ScreenManager screenManager;

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
}
