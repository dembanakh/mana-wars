package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.ui.AssetsFactory;
import com.mana_wars.ui.UIElementsFactory;

final class SkillCaseWindow extends Window {

    private Image skillIcon;
    SkillFactory skillFactory= new SkillFactory();

    SkillCaseWindow(String title, Skin skin) {
        super(title, skin);
        skillIcon = new Image(AssetsFactory.getSkillIcon("image_part", 1));
    }

    Table rebuild(Skin skin) {
        setSkin(skin);
        setFillParent(false);
        setMovable(false);
        setResizable(false);
        add(skillIcon).pad(100).row();
        add(UIElementsFactory.getButton(skin, "GET", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("GET SKILL");
                onGetSkill();
            }
        })).bottom().pad(25, 100, 25, 100).row();
        setVisible(false);
        setDebug(false);
        pack();

        return this;
    }

    private void onGetSkill() {
        setVisible(false);
    }

    void onOpenSkillCase() {
        Skill skill = skillFactory.getSkill();
        prepareSkillCaseWindow(skill);
        setVisible(true);
    }

    private void prepareSkillCaseWindow(Skill skill) {
        skillIcon.setDrawable(new TextureRegionDrawable(AssetsFactory.getSkillIcon(skill.getIconPath(), 1)));
        pack();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        setPosition((screenWidth - getWidth()) * 0.5f,
                (screenHeight - getHeight()) * 0.5f);
    }

}
