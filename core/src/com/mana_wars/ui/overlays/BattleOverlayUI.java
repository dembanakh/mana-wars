package com.mana_wars.ui.overlays;

import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.layout_constraints.AbsoluteSizeConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteXPositionConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteYPositionConstraint;
import com.mana_wars.ui.layout_constraints.RelativeWidthConstraint;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.BuildableUI;
import com.mana_wars.ui.widgets.value_field.BarValueField;
import com.mana_wars.ui.widgets.value_field.ValueField;

import java.util.Arrays;

import io.reactivex.functions.Consumer;

public class BattleOverlayUI extends OverlayUI {

    private final ValueField<Integer> userHealthField;
    private final ValueField<Integer> enemyHealthField;

    BattleOverlayUI(final ScreenSetter screenSetter) {
        userHealthField = new BarValueField()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new AbsoluteSizeConstraint(100));
        enemyHealthField = new BarValueField()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.right, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new AbsoluteSizeConstraint(100));
    }

    @Override
    protected Iterable<BuildableUI> getElements() {
        return Arrays.asList(userHealthField, enemyHealthField);
    }

    public Consumer<? super Integer> getUserHealthObserver() {
        return userHealthField;
    }

    public Consumer<? super Integer> getEnemyHealthObserver() {
        return enemyHealthField;
    }

}
