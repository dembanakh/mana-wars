package com.mana_wars.ui.overlays;

import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.BuildableUI;
import com.mana_wars.ui.widgets.value_field.ManaAmountField;
import com.mana_wars.ui.widgets.value_field.UserLevelField;
import com.mana_wars.ui.widgets.value_field.UsernameField;
import com.mana_wars.ui.widgets.value_field.ValueField;
import com.mana_wars.ui.widgets.NavigationBar;

import java.util.Arrays;

import io.reactivex.functions.Consumer;

public class MenuOverlayUI extends OverlayUI {

    private final ValueField<Integer> manaAmountField;
    private final ValueField<Integer> userLevelField;
    private final ValueField<String> usernameField;
    private final BuildableUI navigationBar;

    MenuOverlayUI(final ScreenSetter screenSetter) {
        manaAmountField = new ManaAmountField();
        userLevelField = new UserLevelField();
        usernameField = new UsernameField();
        navigationBar = new NavigationBar(screenSetter);
    }

    @Override
    protected Iterable<BuildableUI> getElements() {
        return Arrays.asList(navigationBar, manaAmountField, userLevelField, usernameField);
    }

    public Consumer<? super Integer> getManaAmountObserver() {
        return manaAmountField;
    }

    public Consumer<? super Integer> getUserLevelObserver() {
        return userLevelField;
    }

    public Consumer<? super String> getUsernameObserver() {
        return usernameField;
    }
}
