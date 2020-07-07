package com.mana_wars.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.base.BuildableUI;

import static com.mana_wars.ui.UIElementsSize.NAVIGATION_BAR.*;

public class NavigationBar implements BuildableUI {

    private final Table bar;

    private final ScreenSetter screenSetter;

    public NavigationBar(final Skin skin, final ScreenSetter screenSetter) {
        this.screenSetter = screenSetter;
        this.bar = new Table(skin);
        addButton(UIElementFactory.getButton(skin, "MAIN", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onMain();
            }
        }));
        addButton(UIElementFactory.getButton(skin, "SKILLS", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSkills();
            }
        }));
        addButton(UIElementFactory.getButton(skin, "INFO", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: TEMP
                onSkillsInfo();
            }
        }));
        addButton(UIElementFactory.getButton(skin, "DUNGEONS", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onDungeons();
            }
        }));
        init();
    }

    private void init() {
        bar.bottom().setSize(TAB_WIDTH * TABS_NUMBER, TAB_HEIGHT);
        bar.setBackground(UIStringConstants.NAVIGATION_BAR.BG_COLOR);
    }

    private void addButton(TextButton button) {
        bar.add(button).width(TAB_WIDTH).height(TAB_HEIGHT);
    }

    @Override
    public Actor build() {
        return bar;
    }

    private void onMain() {
        screenSetter.setScreen(ScreenInstance.MAIN_MENU, null);
    }

    private void onSkills() {
        screenSetter.setScreen(ScreenInstance.SKILLS, null);
    }

    private void onSkillsInfo() {
        screenSetter.setScreen(ScreenInstance.SKILLS_INFO, null);
    }

    private void onDungeons() {
        screenSetter.setScreen(ScreenInstance.DUNGEONS, null);
    }

}
