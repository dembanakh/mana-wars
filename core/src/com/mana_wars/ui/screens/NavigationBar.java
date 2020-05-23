package com.mana_wars.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;

import static com.mana_wars.ui.screens.UIElementsSize.NAVIGATION_BAR.*;

public class NavigationBar {

    private Table bar;

    private final ScreenManager screenManager;

    private NavigationBar(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void start() { bar = new Table(); }

    Table rebuild(Skin skin) {
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

    public static NavigationBar create(ScreenManager screenManager) {
        return new NavigationBar(screenManager);
    }

}
