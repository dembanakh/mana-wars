package com.mana_wars.ui.overlays;

import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.layout_constraints.AbsoluteSizeConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteXPositionConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteYPositionConstraint;
import com.mana_wars.ui.layout_constraints.RelativeWidthConstraint;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.transform.TransformFactory;
import com.mana_wars.ui.transform.base.Transform;
import com.mana_wars.ui.transform.base.TransformBuilder;
import com.mana_wars.ui.widgets.base.BuildableUI;
import com.mana_wars.ui.widgets.NavigationBar;
import com.mana_wars.ui.widgets.value_field.ValueFieldFactory;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

import java.util.Arrays;

import io.reactivex.functions.Consumer;

import static com.mana_wars.ui.UIElementsSize.MENU_OVERLAY_UI.MANA_AMOUNT_FIELD_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.MENU_OVERLAY_UI.USER_LEVEL_FIELD_HEIGHT;

public class MenuOverlayUI extends BaseOverlayUI {

    private final ValueField<Void, Integer> manaAmountField;
    private final ValueField<Void, Integer> userLevelField;
    private final ValueField<Void, String> usernameField;
    private final BuildableUI navigationBar;

    MenuOverlayUI(final ScreenSetter screenSetter) {
        Transform transform;

        transform = new TransformBuilder()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.right, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new AbsoluteSizeConstraint(200))
                .setHeightConstraint(new AbsoluteSizeConstraint(MANA_AMOUNT_FIELD_HEIGHT()))
                .build();
        manaAmountField = ValueFieldFactory.textValueField(
                UIStringConstants.UI_SKIN.BACKGROUND_COLOR.WHITE,
                TransformFactory.manualTransform(transform));

        transform = new TransformBuilder()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new AbsoluteSizeConstraint(200))
                .setHeightConstraint(new AbsoluteSizeConstraint(USER_LEVEL_FIELD_HEIGHT()))
                .build();
        userLevelField = ValueFieldFactory.textValueField(
                UIStringConstants.UI_SKIN.BACKGROUND_COLOR.WHITE,
                TransformFactory.manualTransform(transform));

        transform = new TransformBuilder()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint())
                .setHeightConstraint(new AbsoluteSizeConstraint(50))
                .build();
        usernameField = ValueFieldFactory.textValueField(TransformFactory.manualTransform(transform));

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

    public void setUsername(final String username) {
        try {
            usernameField.accept(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
