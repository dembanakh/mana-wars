package com.mana_wars.ui.widgets.skill_window;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;

public class SkillInfoWindow extends BaseSkillWindow implements BuildableUI {

    private Skill currentSkill;

    public SkillInfoWindow(String title, Skin skin, AssetFactory<Integer, TextureRegion> iconFactory,
                           AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(title, skin, iconFactory, frameFactory);
    }

    @Override
    public Actor build(Skin skin) {
        super.build(skin);

        Table bottomButtons = new Table();
        bottomButtons.add(UIElementFactory.getButton(skin, "-", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                decreaseLevel();
            }
        })).left();
        bottomButtons.add(UIElementFactory.getButton(skin, UIStringConstants.SKILL_INFO_WINDOW.CLOSE_BUTTON_TEXT,
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        hide();
                    }
                })).padLeft(100).padRight(100);
        bottomButtons.add(UIElementFactory.getButton(skin, "+", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                increaseLevel();
            }
        })).right();
        add(bottomButtons).bottom().width(800);
        pack();

        return this;
    }

    @Override
    void hide() {
        super.hide();
        currentSkill = null;
    }

    @Override
    public void open(Skill skill) {
        currentSkill = skill;
        super.open(skill);
    }

    private void decreaseLevel() {
        currentSkill.downgradeLevel();
        setSkillLevel(currentSkill.getLevel());
        setSkillDescription(currentSkill.getDescription());
    }

    private void increaseLevel() {
        currentSkill.upgradeLevel();
        setSkillLevel(currentSkill.getLevel());
        setSkillDescription(currentSkill.getDescription());
    }

}
