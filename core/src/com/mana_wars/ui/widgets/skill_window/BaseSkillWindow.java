package com.mana_wars.ui.widgets.skill_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.SKILL_DESCRIPTION_PADDING;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.SKILL_ICON_PADDING;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.SKILL_NAME_PADDING;

public abstract class BaseSkillWindow extends Window implements BuildableUI {

    private final Image skillIcon;
    private final Image skillFrame;
    private final Label skillName;
    private final Label skillDescription;

    private final BitmapFont font;
    private int skillLevel;
    private int skillManaCost;

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;

    BaseSkillWindow(String title, Skin skin, AssetFactory<Integer, TextureRegion> iconFactory,
                    AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(title, skin);
        getStyle().titleFont.getData().setScale(2);
        this.skillIcon = new Image();
        this.skillFrame = new Image();
        this.skillName = new Label("", skin);
        this.skillName.setAlignment(Align.center);
        this.skillName.setColor(Color.BLACK);
        this.skillDescription = new Label("", skin);
        this.skillDescription.setAlignment(Align.center | Align.right);
        this.skillDescription.setFontScale(2.5f);
        this.skillDescription.setColor(Color.BLACK);
        this.font = skin.get(Label.LabelStyle.class).font;
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

    /*
     * If overriding this method, you should call pack() afterwards
     */
    @Override
    public Actor build(Skin skin) {
        this.clear();

        setSkin(skin);
        add(skillName).padTop(SKILL_NAME_PADDING).width(800).row();
        Stack stack = new Stack();
        stack.add(skillIcon);
        stack.add(skillFrame);
        add(stack).padTop(SKILL_ICON_PADDING).row();
        add(skillDescription).padTop(SKILL_DESCRIPTION_PADDING).height(400).row();
        pack();
        setVisible(false);
        setDebug(false);

        return this;
    }

    void hide() {
        setVisible(false);
    }

    public void open(Skill skill) {
        open(skill.getIconID(), skill.getName(), skill.getRarity(), skill.getLevel(), skill.getManaCost(), skill.getDescription());
    }

    private void open(int skillID, String skillName, Rarity skillRarity, int skillLevel, int skillManaCost, String skillDescription) {
        skillIcon.setDrawable(new TextureRegionDrawable(iconFactory.getAsset(skillID)));
        skillFrame.setDrawable(new TextureRegionDrawable(frameFactory.getAsset(skillRarity)));
        this.skillName.setText(skillName);
        setSkillLevel(skillLevel);
        this.skillManaCost = skillManaCost;
        setSkillDescription(skillDescription);
        setPosition((SCREEN_WIDTH() - getWidth()) * 0.5f,
                (SCREEN_HEIGHT() - getHeight()) * 0.5f);
        pack();
        setVisible(true);
    }

    void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    void setSkillDescription(String description) {
        skillDescription.setText(description);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Vector2 framePosition = skillFrame.localToStageCoordinates(new Vector2(0, 0));
        float frameWidth = skillFrame.getWidth();

        Vector2 initialFontScale = new Vector2(font.getScaleX(), font.getScaleY());
        font.getData().setScale(3);

        String level = String.valueOf(skillLevel);
        font.setColor(Color.BLACK);
        font.draw(batch, level, framePosition.x - frameWidth, framePosition.y + font.getLineHeight() / 2,
                0, level.length(), frameWidth * 2, Align.center, false, "");

        String manaCost = String.valueOf(skillManaCost);
        font.setColor(Color.BLUE);
        font.draw(batch, manaCost, framePosition.x, framePosition.y + font.getLineHeight() / 2,
                0, manaCost.length(), frameWidth * 2, Align.center, false, "");

        font.getData().setScale(initialFontScale.x, initialFontScale.y);
    }

}
