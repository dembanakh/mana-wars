package com.mana_wars.ui.overlays;

import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.layout_constraints.AbsoluteSizeConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteXPositionConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteYPositionConstraint;
import com.mana_wars.ui.layout_constraints.RelativeWidthConstraint;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.BuildableUI;
import com.mana_wars.ui.widgets.value_field.TextValueField;
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
        manaAmountField = new TextValueField<Integer>()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.right, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new AbsoluteSizeConstraint(200))
                .setHeightConstraint(new AbsoluteSizeConstraint(100))
                .setBackgroundColor("white");
        userLevelField = new TextValueField<Integer>()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new AbsoluteSizeConstraint(200))
                .setHeightConstraint(new AbsoluteSizeConstraint(100))
                .setBackgroundColor("white");
        usernameField = new TextValueField<String>()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint())
                .setHeightConstraint(new AbsoluteSizeConstraint(50));
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
