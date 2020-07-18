package com.mana_wars.ui.widgets.skill_window;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;

public class SkillInfoWindow extends BaseSkillWindow implements BuildableUI {

    public SkillInfoWindow(String title, Skin skin, AssetFactory<Integer, TextureRegion> iconFactory,
                           AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(title, skin, iconFactory, frameFactory);
        init(skin);
    }

    private void init(Skin skin) {
        Table bottomButtons = new Table();
        TextButton button;

        button = UIElementFactory.getButton(skin, "-", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                decreaseLevel();
            }
        });
        bottomButtons.add(button).left();

        button = UIElementFactory.getButton(skin, UIStringConstants.SKILL_INFO_WINDOW.CLOSE_BUTTON_TEXT,
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        hide();
                    }
                });
        bottomButtons.add(button).padLeft(10).padRight(10);

        button = UIElementFactory.getButton(skin, "+", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                increaseLevel();
            }
        });
        bottomButtons.add(button).right();

        add(bottomButtons).bottom().width(800);
    }

    @Override
    public void open(Skill skill) {
        super.open(skill);
    }

    private void decreaseLevel() {
        currentSkill.downgradeLevel();
        setSkillDescription(getDescription(currentSkill));
    }

    private void increaseLevel() {
        currentSkill.upgradeLevel();
        setSkillDescription(getDescription(currentSkill));
    }

}
