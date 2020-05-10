package com.mana_wars.ui.screens;

public class ScreenManager {

    private ScreenHandler handler;

    public ScreenManager(ScreenHandler handler) {
        this.handler = handler;
    }

    public void start() {
        handler.setScreen(ScreenInstance.GREETING.getScreen());
    }

    public void dispose() {
        for (ScreenInstance screenInstance : ScreenInstance.values()) {
            screenInstance.getScreen().dispose();
        }
    }

    enum ScreenInstance {
        GREETING(new GreetingScreen()),
        MAIN_MENU(new MainMenuScreen());

        private final BaseScreen screen;
        ScreenInstance(BaseScreen screen) {
            this.screen = screen;
        }

        public BaseScreen getScreen() { return screen; }
    }

    void setScreen(ScreenInstance screenInstance) {
        handler.setScreen(screenInstance.getScreen());
    }

}
