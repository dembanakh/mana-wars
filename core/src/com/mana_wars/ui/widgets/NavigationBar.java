package com.mana_wars.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;

import static com.mana_wars.ui.UIElementsSize.NAVIGATION_BAR.TABS_NUMBER;
import static com.mana_wars.ui.UIElementsSize.NAVIGATION_BAR.TAB_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.NAVIGATION_BAR.TAB_WIDTH;

public class NavigationBar implements BuildableUI {

    private Table bar;

    private final ScreenSetter screenSetter;

    public NavigationBar(final ScreenSetter screenSetter) {
        this.screenSetter = screenSetter;
    }

    @Override
    public void init() {
        if (bar == null) {
            bar = new Table();
            bar.bottom().setSize(TAB_WIDTH * TABS_NUMBER, TAB_HEIGHT);
        }
    }

    @Override
    public Actor build(final Skin skin) {
        bar.clear();
        bar.setSkin(skin);
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

        bar.add(UIElementFactory.getButton(skin, "PLACEHOLDER", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: TEMP
                screenSetter.setScreen(ScreenInstance.SKILLS_INFO, null);
            }
        })).width(TAB_WIDTH).height(TAB_HEIGHT);
        // PLACEHOLDER3
        bar.add(UIElementFactory.getButton(skin, "DUNGEONS", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onDungeons();
            }
        })).width(TAB_WIDTH).height(TAB_HEIGHT);

        return bar;
    }

    private void onMain() {
        screenSetter.setScreen(ScreenInstance.MAIN_MENU, null);
    }

    private void onSkills() {
        screenSetter.setScreen(ScreenInstance.SKILLS, null);
    }

    private void onDungeons() {
        screenSetter.setScreen(ScreenInstance.DUNGEONS, null);
    }

}
