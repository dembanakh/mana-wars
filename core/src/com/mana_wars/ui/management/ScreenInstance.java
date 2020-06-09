package com.mana_wars.ui.management;

import com.mana_wars.ui.overlays.OverlayUIFactory;
import com.mana_wars.ui.screens.BaseScreen;
import com.mana_wars.ui.screens.BattleScreen;
import com.mana_wars.ui.screens.GreetingScreen;
import com.mana_wars.ui.screens.LoadingScreen;
import com.mana_wars.ui.screens.MainMenuScreen;
import com.mana_wars.ui.screens.SkillsScreen;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.storage.UpdaterStorage;

import java.util.Map;

public enum ScreenInstance {
    LOADING,
    GREETING,
    MAIN_MENU,
    SKILLS,
    BATTLE;

    private BaseScreen screen;

    public static void init(final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                            final RepositoryStorage repositoryStorage,
                            final OverlayUIFactory overlayUIFactory,
                            final UpdaterStorage updaterStorage) {
        LOADING.screen = new LoadingScreen(screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getEmptyOverlayUI(), updaterStorage);
        GREETING.screen = new GreetingScreen(screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getEmptyOverlayUI());
        MAIN_MENU.screen = new MainMenuScreen(screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getMenuOverlayUI());
        SKILLS.screen = new SkillsScreen(screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getMenuOverlayUI());
        BATTLE.screen = new BattleScreen(screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getBattleOverlayUI());
    }

    public static void dispose() {
        for (ScreenInstance screenInstance : ScreenInstance.values()) {
            screenInstance.screen.dispose();
        }
    }

    public BaseScreen getScreen(Map<String, Object> arguments) { return screen.reInit(arguments); }
}
