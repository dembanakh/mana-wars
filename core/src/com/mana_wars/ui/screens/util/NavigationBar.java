package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.screens.ScreenManager;

import static com.mana_wars.ui.screens.util.UIElementsSize.NAVIGATION_BAR.*;

class NavigationBar implements BuildableUI {

    private Table bar;

    private final ScreenManager screenManager;

    NavigationBar(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void init() {
        if (bar == null) bar = new Table();
    }

    @Override
    public Actor build(Skin skin) {
        bar.clear();
        bar.setSkin(skin);
        bar.bottom().setSize(TAB_WIDTH * TABS_NUMBER, TAB_HEIGHT);
        bar.setBackground(UIStringConstants.NAVIGATION_BAR.BG_COLOR);

        bar.add(UIElementFactory.getButton(skin, "MAIN", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onMain();
            }
        })).width(TAB_WIDTH).height(TAB_HEIGHT);

        bar.add(UIElementFactory.getButton(skin, "SKILLS", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSkills();
            }
        })).width(TAB_WIDTH).height(TAB_HEIGHT);

        bar.add(UIElementFactory.getButton(skin, "BATTLE", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onBattle();
            }
        })).width(TAB_WIDTH).height(TAB_HEIGHT);
        // PLACEHOLDER3
        bar.add(UIElementFactory.getButton(skin, "PLACEHOLDER3", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        })).width(TAB_WIDTH).height(TAB_HEIGHT);

        return bar;
    }

    private void onMain() {
        screenManager.setScreen(ScreenManager.ScreenInstance.MAIN_MENU);
    }

    private void onSkills() {
        screenManager.setScreen(ScreenManager.ScreenInstance.SKILLS);
    }

    private void onBattle() {
        screenManager.setScreen(ScreenManager.ScreenInstance.TEST_BATTLE);
    }

}
