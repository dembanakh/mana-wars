package com.mana_wars.ui.screens;

import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.screens.util.OverlayUIFactory;

import java.util.Map;

public interface ScreenManager {

    void setScreen(ScreenInstance screenInstance);

    enum ScreenInstance {
        GREETING,
        MAIN_MENU,
        SKILLS,
        TEST_BATTLE;

        private BaseScreen screen;

        public static void init(final ScreenManager screenManager, final FactoryStorage factoryStorage,
                                final RepositoryStorage repositoryStorage,
                                final OverlayUIFactory overlayUIFactory) {
            GREETING.screen = new GreetingScreen(screenManager, factoryStorage, repositoryStorage,
                    overlayUIFactory.getEmptyOverlayUI());
            MAIN_MENU.screen = new MainMenuScreen(screenManager, factoryStorage, repositoryStorage,
                    overlayUIFactory.getMenuOverlayUI(), overlayUIFactory.getMenuOverlayUI());
            SKILLS.screen = new SkillsScreen(screenManager, factoryStorage, repositoryStorage,
                    overlayUIFactory.getMenuOverlayUI(), overlayUIFactory.getMenuOverlayUI());
            TEST_BATTLE.screen = new TestBattleScreen(screenManager, factoryStorage, repositoryStorage,
                    overlayUIFactory.getEmptyOverlayUI());
        }

        public static void dispose() {
            for (ScreenInstance screenInstance : ScreenInstance.values()) {
                screenInstance.screen.dispose();
            }
        }

        private Map<String, Object> arguments;
        public void setArguments(final Map<String, Object> arguments) {
            this.arguments = arguments;
        }

        public BaseScreen getScreen() { return screen.setArguments(arguments); }
    }

}
