package com.mana_wars.ui.screens;

public class ScreenManager {

    private ScreenHandler handler;

    private com.mana_wars.ui.screens.BaseScreen mainMenuScreen;

    public ScreenManager(ScreenHandler handler) {
        this.handler = handler;
    }

    public void start() {
        mainMenuScreen = new MainMenuScreen();
        handler.setScreen(mainMenuScreen);
    }

    public void dispose() {
        mainMenuScreen.dispose();
    }

}
