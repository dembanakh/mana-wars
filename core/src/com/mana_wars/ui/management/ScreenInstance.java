package com.mana_wars.ui.management;

import com.mana_wars.model.entity.user.User;
import com.mana_wars.model.repository.ApplicationDataUpdater;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.overlays.OverlayUIFactory;
import com.mana_wars.ui.screens.BaseScreen;
import com.mana_wars.ui.screens.BattleScreen;
import com.mana_wars.ui.screens.BattleSummaryScreen;
import com.mana_wars.ui.screens.DungeonsScreen;
import com.mana_wars.ui.screens.GreetingScreen;
import com.mana_wars.ui.screens.LoadingScreen;
import com.mana_wars.ui.screens.MainMenuScreen;
import com.mana_wars.ui.screens.ShopScreen;
import com.mana_wars.ui.screens.SkillsInfoScreen;
import com.mana_wars.ui.screens.SkillsScreen;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;

import java.util.Map;

public enum ScreenInstance {
    LOADING,
    GREETING,
    MAIN_MENU,
    SKILLS,
    SKILLS_INFO,
    DUNGEONS,
    BATTLE,
    BATTLE_SUMMARY,
    SHOP;

    private BaseScreen screen;

    static void init(final User user,
                            final ScreenSetter screenSetter,
                            final FactoryStorage factoryStorage,
                            final RepositoryStorage repositoryStorage,
                            final ApplicationDataUpdater applicationDataUpdater) {
        OverlayUIFactory overlayUIFactory = new OverlayUIFactory(factoryStorage.getSkinFactory(), screenSetter,
                factoryStorage.getLocalizedStringFactory());
        LOADING.screen = new LoadingScreen(screenSetter, factoryStorage,
                overlayUIFactory.getEmptyOverlayUI(), applicationDataUpdater);
        GREETING.screen = new GreetingScreen(user, screenSetter, factoryStorage,
                overlayUIFactory.getEmptyOverlayUI());
        MAIN_MENU.screen = new MainMenuScreen(user,
                factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.MANA_WARS),
                screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getMenuOverlayUI());
        SKILLS.screen = new SkillsScreen(user,
                factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.MANA_WARS),
                screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getMenuOverlayUI());
        SKILLS_INFO.screen = new SkillsInfoScreen(
                factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.MANA_WARS),
                screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getEmptyOverlayUI());
        DUNGEONS.screen = new DungeonsScreen(user,
                factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.MANA_WARS),
                screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getEmptyOverlayUI());
        BATTLE.screen = new BattleScreen(user,
                factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.MANA_WARS),
                screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                overlayUIFactory.getEmptyOverlayUI());
        BATTLE_SUMMARY.screen = new BattleSummaryScreen(user,
                factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.MANA_WARS),
                screenSetter, factoryStorage, repositoryStorage,
                overlayUIFactory.getEmptyOverlayUI());
        SHOP.screen = new ShopScreen(user,
                factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.MANA_WARS),
                screenSetter, factoryStorage, repositoryStorage.getDatabaseRepository(),
                repositoryStorage.getShopRepository(),
                overlayUIFactory.getMenuOverlayUI());
    }

    static void dispose() {
        for (ScreenInstance screenInstance : ScreenInstance.values()) {
            screenInstance.screen.dispose();
        }
    }

    BaseScreen getScreen(Map<String, Object> arguments) {
        return screen.reInit(arguments);
    }
}
