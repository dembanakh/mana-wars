package com.mana_wars.view.screens;

public class ScreenManager {

    private ScreenHandler handler;

    private BaseScreen mainMenuScreen;

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
