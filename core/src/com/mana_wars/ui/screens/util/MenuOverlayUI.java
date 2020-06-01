package com.mana_wars.ui.screens.util;

import com.mana_wars.model.repository.UserLevelRepository;
import com.mana_wars.model.repository.UserManaRepository;
import com.mana_wars.model.repository.UsernameRepository;
import com.mana_wars.ui.callback.MenuOverlayUICallbacks;
import com.mana_wars.ui.screens.OverlayUI;
import com.mana_wars.ui.screens.ScreenManager;

import java.util.Arrays;

import io.reactivex.functions.Consumer;

class MenuOverlayUI extends OverlayUI implements MenuOverlayUICallbacks {

    private final Consumer<? super Integer> manaAmountCallback;
    private final Consumer<? super Integer> userLevelCallback;
    private final Consumer<? super String> usernameCallback;

    MenuOverlayUI(final ScreenManager screenManager) {
        super();
        ManaAmountField manaAmountField = new ManaAmountField();
        manaAmountCallback = manaAmountField::setManaAmount;
        UserLevelField userLevelField = new UserLevelField();
        userLevelCallback = userLevelField::setUserLevel;
        UsernameField usernameField = new UsernameField();
        usernameCallback = usernameField::setUsername;
        BuildableUI navigationBar = new NavigationBar(screenManager);

        elements.addAll(Arrays.asList(navigationBar, manaAmountField, userLevelField, usernameField));
    }

    void init(final UserManaRepository userManaRepository,
              final UserLevelRepository userLevelRepository,
              final UsernameRepository usernameRepository) {
        for (BuildableUI element : elements)
            element.init();

        try {
            usernameCallback.accept(usernameRepository.getUsername());
            manaAmountCallback.accept(userManaRepository.getUserMana());
            userLevelCallback.accept(userLevelRepository.getUserLevel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Consumer<? super Integer> getUserLevelCallback() {
        return userLevelCallback;
    }

    @Override
    public Consumer<? super Integer> getManaAmountCallback() {
        return manaAmountCallback;
    }
}
