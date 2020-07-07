package com.mana_wars.ui.management;

import com.mana_wars.model.entity.user.User;
import com.mana_wars.ui.overlays.OverlayUIFactory;
import com.mana_wars.ui.screens.BaseScreen;
import com.mana_wars.ui.screens.BattleScreen;
import com.mana_wars.ui.screens.BattleSummaryScreen;
import com.mana_wars.ui.screens.DungeonsScreen;
import com.mana_wars.ui.screens.GreetingScreen;
import com.mana_wars.ui.screens.MainMenuScreen;
import com.mana_wars.ui.screens.ShopScreen;
import com.mana_wars.ui.screens.SkillsInfoScreen;
import com.mana_wars.ui.screens.SkillsScreen;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;

import java.util.Map;

public enum ScreenInstance {
    GREETING,
    MAIN_MENU,
    SKILLS,
    SKILLS_INFO,
    DUNGEONS,
    BATTLE,
    BATTLE_SUMMARY,
    SHOP;

    private BaseScreen screen;

    public static void init(final User user,
                            final ScreenSetter screenSetter,
                            final FactoryStorage factoryStorage,
                            final RepositoryStorage repositoryStorage,
                            final OverlayUIFactory overlayUIFactory) {
        GREETING.screen = new GreetingScreen(user, screenSetter, factoryStorage,
                overlayUIFactory.getEmptyOverlayUI());
        MAIN_MENU.screen = new MainMenuScreen(user, screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getMenuOverlayUI());
        SKILLS.screen = new SkillsScreen(user, screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getMenuOverlayUI());
        SKILLS_INFO.screen = new SkillsInfoScreen(screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getEmptyOverlayUI());
        DUNGEONS.screen = new DungeonsScreen(user, screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getEmptyOverlayUI());
        BATTLE.screen = new BattleScreen(user, screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getEmptyOverlayUI());
        BATTLE_SUMMARY.screen = new BattleSummaryScreen(user, screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getEmptyOverlayUI());
        SHOP.screen = new ShopScreen(user, screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getMenuOverlayUI());
    }

    public static void dispose() {
        for (ScreenInstance screenInstance : ScreenInstance.values()) {
            screenInstance.screen.dispose();
        }
    }

    public BaseScreen getScreen(Map<String, Object> arguments) {
        return screen.reInit(arguments);
    }
}
