package com.mana_wars.ui.overlays;

import com.mana_wars.model.repository.UserLevelRepository;
import com.mana_wars.model.repository.UserManaRepository;
import com.mana_wars.model.repository.UsernameRepository;
import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.screens.util.BuildableUI;
import com.mana_wars.ui.screens.util.ManaAmountField;
import com.mana_wars.ui.screens.util.UserLevelField;
import com.mana_wars.ui.screens.util.UsernameField;

import java.util.Arrays;

import io.reactivex.functions.Consumer;

public class MenuOverlayUI extends OverlayUI {

    private final Consumer<? super Integer> manaAmountCallback;
    private final Consumer<? super Integer> userLevelCallback;
    private final Consumer<? super String> usernameCallback;

    public MenuOverlayUI(final ScreenManager screenManager) {
        super();
        ManaAmountField manaAmountField = new ManaAmountField();
        manaAmountCallback = manaAmountField::setManaAmount;
        UserLevelField userLevelField = new UserLevelField();
        userLevelCallback = userLevelField::setUserLevel;
        UsernameField usernameField = new UsernameField();
        usernameCallback = usernameField::setUsername;
        BuildableUI navigationBar = new com.mana_wars.ui.widgets.NavigationBar(screenManager);

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

    public Consumer<? super Integer> getUserLevelCallback() {
        return userLevelCallback;
    }

    public Consumer<? super Integer> getManaAmountCallback() {
        return manaAmountCallback;
    }
}
