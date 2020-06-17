package com.mana_wars.ui.widgets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.SKILL_DESCRIPTION_PADDING;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.SKILL_ICON_PADDING;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.SKILL_NAME_PADDING;

public class SkillInfoWindow extends Window implements BuildableUI {

    private final Image skillIcon;
    private final Image skillFrame;
    private final Label skillName;
    private final Label skillDescription;
    private final Label skillManaCost;

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;

    private Skill currentSkill;

    public SkillInfoWindow(String title, Skin skin, AssetFactory<Integer, TextureRegion> iconFactory,
                           AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(title, skin);
        this.skillIcon = new Image();
        this.skillFrame = new Image();
        this.skillName = new Label("", skin);
        this.skillName.setAlignment(Align.center);
        this.skillDescription = new Label("", skin);
        this.skillDescription.setAlignment(Align.center | Align.right);
        this.skillDescription.setFontScale(1.5f);
        this.skillManaCost = new Label("", skin);
        this.skillManaCost.setFontScale(3);
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        padTop(32);
    }

    @Override
    public void init() {
        setFillParent(false);
        setMovable(false);
        setResizable(false);
    }

    @Override
    public Actor build(Skin skin) {
        this.clear();

        setSkin(skin);
        add(skillName).padTop(SKILL_NAME_PADDING).width(800).row();
        Stack stack = new Stack();
        stack.add(skillIcon);
        stack.add(skillFrame);
        stack.add(skillManaCost);
        add(stack).padTop(SKILL_ICON_PADDING).row();
        add(skillDescription).padTop(SKILL_DESCRIPTION_PADDING).height(400).row();

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
        setVisible(false);
        setDebug(false);

        return this;
    }

    private void hide() {
        currentSkill = null;
        setVisible(false);
    }

    public void open(Skill skill) {
        currentSkill = skill;
        open(skill.getIconID(), skill.getName(), skill.getRarity(), skill.getManaCost(), skill.getDescription());
    }

    private void decreaseLevel() {
        currentSkill.downgradeLevel();
        skillDescription.setText(currentSkill.getDescription());
    }

    private void increaseLevel() {
        currentSkill.upgradeLevel();
        skillDescription.setText(currentSkill.getDescription());
    }

    private void open(int skillID, String skillName, Rarity skillRarity, int skillManaCost, String skillDescription) {
        skillIcon.setDrawable(new TextureRegionDrawable(iconFactory.getAsset(skillID)));
        skillFrame.setDrawable(new TextureRegionDrawable(frameFactory.getAsset(skillRarity)));
        this.skillName.setText(skillName);
        this.skillManaCost.setText(skillManaCost);
        this.skillDescription.setText(skillDescription);
        setPosition((SCREEN_WIDTH() - getWidth()) * 0.5f,
                (SCREEN_HEIGHT() - getHeight()) * 0.5f);
        pack();
        setVisible(true);
    }

}
