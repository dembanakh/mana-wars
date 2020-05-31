package com.mana_wars.ui.callback;

import io.reactivex.functions.Consumer;

public interface MenuOverlayUICallbacks {
    Consumer<? super Integer> getUserLevelCallback();
    Consumer<? super Integer> getManaAmountCallback();
}
