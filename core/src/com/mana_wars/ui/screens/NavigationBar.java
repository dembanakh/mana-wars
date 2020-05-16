package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ManaWars;
import com.mana_wars.ui.factory.UIElementFactory;

class NavigationBar {

    private Table bar;

    private final ScreenManager screenManager;

    NavigationBar(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    Table rebuild(Skin skin) {
        bar = new Table(skin);
        bar.bottom().setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.1f);
        bar.setBackground("white");

        int tabsNumber = 4;
        float buttonWidth = (float)Gdx.graphics.getWidth() / tabsNumber;
        float buttonHeight = bar.getHeight();

        // SKILLS
        bar.add(UIElementFactory.getButton(skin, "MAIN", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("MAIN");
                onMain();
            }
        })).width(buttonWidth).height(buttonHeight);
        // SKILLS
        bar.add(UIElementFactory.getButton(skin, "SKILLS", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("SKILLS");
                onSkills();
            }
        })).width(buttonWidth).height(buttonHeight);
        // PLACEHOLDER2
        bar.add(UIElementFactory.getButton(skin, "PLACEHOLDER2", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("PLACEHOLDER2");
            }
        })).width(buttonWidth).height(buttonHeight);
        // PLACEHOLDER3
        bar.add(UIElementFactory.getButton(skin, "PLACEHOLDER3", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("PLACEHOLDER3");
            }
        })).width(buttonWidth).height(buttonHeight);

        return bar;
    }

    private void onMain() {
        screenManager.setScreen(ScreenManager.ScreenInstance.MAIN_MENU);
    }

    private void onSkills() {
        screenManager.setScreen(ScreenManager.ScreenInstance.SKILLS);
    }

}
