package com.mana_wars.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.LocalizedStringFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.base.BuildableUI;

import static com.mana_wars.ui.UIElementsSize.NAVIGATION_BAR.*;
import static com.mana_wars.ui.UIStringConstants.NAVIGATION_BAR.DUNGEONS_KEY;
import static com.mana_wars.ui.UIStringConstants.NAVIGATION_BAR.INFO_KEY;
import static com.mana_wars.ui.UIStringConstants.NAVIGATION_BAR.MAIN_KEY;
import static com.mana_wars.ui.UIStringConstants.NAVIGATION_BAR.SHOP_KEY;
import static com.mana_wars.ui.UIStringConstants.NAVIGATION_BAR.SKILLS_KEY;

public class NavigationBar implements BuildableUI {

    private final Table bar;

    private final ScreenSetter screenSetter;

    public NavigationBar(final Skin skin, final ScreenSetter screenSetter,
                         final LocalizedStringFactory localizedStringFactory) {
        this.screenSetter = screenSetter;
        this.bar = new Table(skin);
        addButton(UIElementFactory.getButton(skin, localizedStringFactory.get(MAIN_KEY), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onMain();
            }
        }));
        addButton(UIElementFactory.getButton(skin, localizedStringFactory.get(SKILLS_KEY), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSkills();
            }
        }));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        style.font = skin.getFont("font");
        style.font.getData().setScale(2);
        addButton(UIElementFactory.getButton(style, localizedStringFactory.get(DUNGEONS_KEY), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onDungeons();
            }
        }));
        addButton(UIElementFactory.getButton(skin, localizedStringFactory.get(INFO_KEY), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSkillsInfo();
            }
        }));
        addButton(UIElementFactory.getButton(skin, localizedStringFactory.get(SHOP_KEY), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onShop();
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

    private void onShop() {
        screenSetter.setScreen(ScreenInstance.SHOP, null);
    }

}
