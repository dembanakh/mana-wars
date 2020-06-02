package com.mana_wars.ui.overlays;

import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.screens.util.BuildableUI;
import com.mana_wars.ui.screens.util.ManaAmountField;
import com.mana_wars.ui.screens.util.UserLevelField;
import com.mana_wars.ui.screens.util.UsernameField;
import com.mana_wars.ui.screens.util.ValueField;
import com.mana_wars.ui.widgets.NavigationBar;

import java.util.Arrays;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

public class MenuOverlayUI extends OverlayUI {

    private final ValueField<Integer> manaAmountField;
    private final ValueField<Integer> userLevelField;
    private final ValueField<String> usernameField;
    private final BuildableUI navigationBar;

    MenuOverlayUI(final ScreenManager screenManager) {
        super();
        manaAmountField = new ManaAmountField();
        userLevelField = new UserLevelField();
        usernameField = new UsernameField();
        navigationBar = new NavigationBar(screenManager);
    }

    public void addObservers(CompositeDisposable disposable, Subject<Integer> manaAmountObservable,
                             Subject<Integer> userLevelObservable, Subject<String> usernameObservable) {
        disposable.add(manaAmountObservable.subscribe(manaAmountField));
        disposable.add(userLevelObservable.subscribe(userLevelField));
        disposable.add(usernameObservable.subscribe(usernameField));
    }

    @Override
    protected Iterable<BuildableUI> getElements() {
        return Arrays.asList(navigationBar, manaAmountField, userLevelField, usernameField);
    }
}
