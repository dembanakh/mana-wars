package com.mana_wars.ui.screens;

import com.mana_wars.ui.screens.util.OverlayUIFactory;

import java.util.Map;

public interface ScreenManager {

    void setScreen(ScreenInstance screenInstance);

    enum ScreenInstance {
        GREETING(new GreetingScreen()),
        MAIN_MENU(new MainMenuScreen()),
        SKILLS(new SkillsScreen()),
        TEST_BATTLE(new TestBattleScreen());

        private final BaseScreen screen;
        ScreenInstance(BaseScreen screen) {
            this.screen = screen;
        }

        public static void init(ScreenManager screenManager, FactoryStorage factoryStorage,
                                RepositoryStorage repositoryStorage, OverlayUIFactory overlayUIFactory) {
            for (ScreenInstance instance : values()) {
                instance.screen.init(screenManager, factoryStorage, repositoryStorage,
                        overlayUIFactory.getOverlayUI(instance));
            }
        }

        private Map<String, Object> arguments;
        public void setArguments(Map<String, Object> arguments) {
            this.arguments = arguments;
        }

        public BaseScreen getScreen() { return screen.setArguments(arguments); }
        public void dispose() {
            screen.dispose();
        }
    }

}
