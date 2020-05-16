package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mana_wars.ManaWars;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;

final class SkillCaseWindow extends Window {

    private Image skillIcon;
    private Label skillName;
    private Label skillDescription;

    private AssetFactory<Integer, TextureRegion> skillIconsFactory;

    SkillCaseWindow(String title, Skin skin) {
        super(title, skin);
        this.skillIcon = new Image();
        this.skillName = new Label("", skin);
        this.skillName.setFontScale(1.5f);
        this.skillDescription = new Label("", skin);
        this.skillDescription.setFontScale(1.5f);
        this.skillIconsFactory = ManaWars.getInstance().getScreenManager().getSkillIconFactory();
        padTop(32);
    }

    Table rebuild(Skin skin) {
        setSkin(skin);
        setFillParent(false);
        setMovable(false);
        setResizable(false);
        add(skillName).padTop(10).row();
        add(skillIcon).padTop(50).row();
        add(skillDescription).padTop(50).row();
        add(UIElementFactory.getButton(skin, "GET", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hideSkill();
            }
        })).bottom().pad(50, 100, 25, 100);
        pack();
        setVisible(false);
        setDebug(false);

        return this;
    }

    private void hideSkill() {
        setVisible(false);
    }

    void open(int skillID, String skillName, String skillDescription) {
        skillIcon.setDrawable(new TextureRegionDrawable(skillIconsFactory.getAsset(skillID)));
        this.skillName.setText(skillName);
        this.skillDescription.setText(skillDescription);
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        setPosition((screenWidth - getWidth()) * 0.5f,
                (screenHeight - getHeight()) * 0.5f);
        pack();
        setVisible(true);
    }

}
