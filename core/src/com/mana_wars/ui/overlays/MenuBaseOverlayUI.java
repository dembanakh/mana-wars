package com.mana_wars.ui.overlays;

import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.layout_constraints.AbsoluteSizeConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteXPositionConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteYPositionConstraint;
import com.mana_wars.ui.layout_constraints.RelativeWidthConstraint;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.BuildableUI;
import com.mana_wars.ui.widgets.NavigationBar;
import com.mana_wars.ui.widgets.value_field.TextValueField;
import com.mana_wars.ui.widgets.value_field.ValueField;

import java.util.Arrays;

import io.reactivex.functions.Consumer;

import static com.mana_wars.ui.UIElementsSize.MENU_OVERLAY_UI.MANA_AMOUNT_FIELD_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.MENU_OVERLAY_UI.USER_LEVEL_FIELD_HEIGHT;

public class MenuBaseOverlayUI extends BaseOverlayUI {

    private final ValueField<Integer> manaAmountField;
    private final ValueField<Integer> userLevelField;
    private final ValueField<String> usernameField;
    private final BuildableUI navigationBar;

    MenuBaseOverlayUI(final ScreenSetter screenSetter) {
        manaAmountField = new TextValueField<Integer>()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.right, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setBackgroundColor("white");
        userLevelField = new TextValueField<Integer>()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setBackgroundColor("white");
        usernameField = new TextValueField<String>()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint())
                .setHeightConstraint(new AbsoluteSizeConstraint(50));
        navigationBar = new NavigationBar(screenSetter);
    }

    @Override
    public void init() {
        manaAmountField
                .setWidthConstraint(new AbsoluteSizeConstraint(200))
                .setHeightConstraint(new AbsoluteSizeConstraint(MANA_AMOUNT_FIELD_HEIGHT()));
        userLevelField
                .setWidthConstraint(new AbsoluteSizeConstraint(200))
                .setHeightConstraint(new AbsoluteSizeConstraint(USER_LEVEL_FIELD_HEIGHT()));
        super.init();
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

    public void setUsername(final String username) {
        try {
            usernameField.accept(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
