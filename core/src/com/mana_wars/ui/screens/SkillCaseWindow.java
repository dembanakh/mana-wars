package com.mana_wars.ui.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;

import java.util.List;

import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_WIDTH;
import static com.mana_wars.ui.screens.util.UIElementsSize.SKILL_CASE_WINDOW.*;

class SkillCaseWindow extends Window {

    private final Image skillIcon;
    private final Image skillFrame;
    private final Label skillName;
    private final Label skillDescription;

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;

    SkillCaseWindow(String title, Skin skin, AssetFactory<Integer, TextureRegion> iconFactory,
                    AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(title, skin);
        this.skillIcon = new Image();
        this.skillFrame = new Image();
        this.skillName = new Label("", skin);
        this.skillDescription = new Label("", skin);
        this.skillDescription.setFontScale(1.5f);
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        padTop(32);
    }

    Actor rebuild(Skin skin) {
        this.clear();
        
        setSkin(skin);
        setFillParent(false);
        setMovable(false);
        setResizable(false);
        add(skillName).padTop(SKILL_NAME_PADDING).row();
        Stack stack = new Stack();
        stack.add(skillIcon);
        stack.add(skillFrame);
        add(stack).padTop(SKILL_ICON_PADDING).row();
        add(skillDescription).padTop(SKILL_DESCRIPTION_PADDING).row();
        add(UIElementFactory.getButton(skin, UIStringConstants.SKILL_CASE_WINDOW.CLOSE_BUTTON_TEXT,
                new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hideSkill();
            }
        })).bottom().pad(GET_BUTTON_PADDING_TOP, GET_BUTTON_PADDING_LEFT,
                GET_BUTTON_PADDING_BOTTOM, GET_BUTTON_PADDING_RIGHT);
        pack();
        setVisible(false);
        setDebug(false);

        return this;
    }

    private void hideSkill() {
        setVisible(false);
    }

    void open(int skillID, String skillName, Rarity skillRarity, List<String> skillDescription) {
        skillIcon.setDrawable(new TextureRegionDrawable(iconFactory.getAsset(skillID)));
        skillFrame.setDrawable(new TextureRegionDrawable(frameFactory.getAsset(skillRarity)));
        this.skillName.setText(skillName);
        this.skillDescription.setText(skillDescription.get(0));
        setPosition((SCREEN_WIDTH - getWidth()) * 0.5f,
                (SCREEN_HEIGHT - getHeight()) * 0.5f);
        pack();
        setVisible(true);
    }

}
