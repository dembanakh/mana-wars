package com.mana_wars.ui.overlays;

import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.BuildableUI;
import com.mana_wars.ui.widgets.value_field.HealthField;
import com.mana_wars.ui.widgets.value_field.ValueField;

import java.util.Arrays;

import io.reactivex.functions.Consumer;

public class BattleOverlayUI extends OverlayUI {

    private final ValueField<Integer> userHealthField;
    private final ValueField<Integer> enemyHealthField;

    BattleOverlayUI(final ScreenSetter screenSetter) {
        userHealthField = new HealthField(100,50);
        enemyHealthField = new HealthField(100,100);
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
