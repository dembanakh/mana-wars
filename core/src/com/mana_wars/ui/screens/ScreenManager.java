package com.mana_wars.ui.screens;

import com.mana_wars.ManaWars;

public interface ScreenManager {

    NavigationBar getNavigationBar();
    void setScreen(ScreenInstance screenInstance);

    enum ScreenInstance {
        GREETING(new GreetingScreen(ManaWars.getInstance().getScreenManager(),
                                    ManaWars.getInstance().getScreenManager())),
        MAIN_MENU(new MainMenuScreen(ManaWars.getInstance().getScreenManager(),
                                    ManaWars.getInstance().getScreenManager(),
                                    ManaWars.getInstance().getLocalUserDataRepository(),
                                    ManaWars.getInstance().getDatabaseRepository())),
        SKILLS(new SkillsScreen(ManaWars.getInstance().getScreenManager(),
                                ManaWars.getInstance().getScreenManager(),
                                ManaWars.getInstance().getDatabaseRepository())),
        TEST_BATTLE(new TestBattleScreen(ManaWars.getInstance().getScreenManager(),
                                ManaWars.getInstance().getScreenManager(),
                                ManaWars.getInstance().getLocalUserDataRepository(),
                                ManaWars.getInstance().getDatabaseRepository()));

        private final BaseScreen screen;
        ScreenInstance(BaseScreen screen) {
            this.screen = screen;
        }

        public BaseScreen getScreen() { return screen; }
        public void dispose() {
            screen.dispose();
        }
    }

}
