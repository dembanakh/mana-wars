package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mana_wars.ui.AssetsFactory;
import com.mana_wars.ui.UIElementsFactory;

final class SkillCaseWindow extends Window {

    private Image skillIcon;
    private Label skillName;

    SkillCaseWindow(String title, Skin skin) {
        super(title, skin);
        this.skillIcon = new Image();
        this.skillName = new Label("", skin);
    }

    Table rebuild(Skin skin) {
        setSkin(skin);
        setFillParent(false);
        setMovable(false);
        setResizable(false);
        add(skillName).pad(10).row();
        add(skillIcon).pad(100).row();
        add(UIElementsFactory.getButton(skin, "GET", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hideSkill();
            }
        })).bottom().pad(25, 100, 25, 100).row();
        setVisible(false);
        setDebug(false);
        pack();

        return this;
    }

    private void hideSkill() {
        setVisible(false);
    }

    //TODO implement description
    void showSkill(int skillID, String skillName, String description) {
        skillIcon.setDrawable(new TextureRegionDrawable(AssetsFactory.getSkillIcon(skillID)));
        this.skillName.setText(skillName);
        pack();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        setPosition((screenWidth - getWidth()) * 0.5f,
                (screenHeight - getHeight()) * 0.5f);
        setVisible(true);
    }

}
