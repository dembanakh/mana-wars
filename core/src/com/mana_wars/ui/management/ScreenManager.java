package com.mana_wars.ui.management;

import com.mana_wars.ui.screens.BaseScreen;
import com.mana_wars.ui.screens.BattleScreen;
import com.mana_wars.ui.screens.GreetingScreen;
import com.mana_wars.ui.screens.MainMenuScreen;
import com.mana_wars.ui.screens.SkillsScreen;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.overlays.OverlayUIFactory;

import java.util.Map;

public interface ScreenManager {

    void setScreen(ScreenInstance screenInstance);

    enum ScreenInstance {
        GREETING,
        MAIN_MENU,
        SKILLS,
        BATTLE;

        private BaseScreen screen;

        public static void construct(final ScreenManager screenManager, final FactoryStorage factoryStorage,
                                     final RepositoryStorage repositoryStorage,
                                     final OverlayUIFactory overlayUIFactory) {
            GREETING.screen = new GreetingScreen(screenManager, factoryStorage, repositoryStorage,
                    overlayUIFactory.getEmptyOverlayUI());
            MAIN_MENU.screen = new MainMenuScreen(screenManager, factoryStorage, repositoryStorage,
                    overlayUIFactory.getMenuOverlayUI());
            SKILLS.screen = new SkillsScreen(screenManager, factoryStorage, repositoryStorage,
                    overlayUIFactory.getMenuOverlayUI());
            BATTLE.screen = new BattleScreen(screenManager, factoryStorage, repositoryStorage,
                    overlayUIFactory.getEmptyOverlayUI());
        }

        public static void init() {
            for (ScreenInstance screenInstance : ScreenInstance.values()) {
                screenInstance.screen.init();
            }
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
