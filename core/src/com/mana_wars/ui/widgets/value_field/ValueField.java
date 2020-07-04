package com.mana_wars.ui.widgets.value_field;

import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.widgets.base.BuildableUI;

import io.reactivex.functions.Consumer;

public interface ValueField<T> extends ManualBackground, BuildableUI, Consumer<T> {
    @Override
    ValueField<T> setBackgroundColor(UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor);
}
