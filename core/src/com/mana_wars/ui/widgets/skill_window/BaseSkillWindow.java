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
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.skills.ReadableSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;
import com.mana_wars.ui.widgets.base.ListItemDrawer;
import com.mana_wars.ui.widgets.item_drawer.SkillLevelDrawer;
import com.mana_wars.ui.widgets.item_drawer.SkillManaCostDrawer;
import com.mana_wars.ui.widgets.item_drawer.SkillTypeDrawer;

import java.util.ArrayList;
import java.util.List;

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
    Skill currentSkill;

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;

    private static final List<ListItemDrawer<? super ReadableSkill>> Components = new ArrayList<>();

    BaseSkillWindow(String title, Skin skin,
                    AssetFactory<Integer, TextureRegion> iconFactory,
                    AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(title, skin);
        this.skillIcon = new Image();
        this.skillFrame = new Image();
        this.skillName = new Label("", skin);
        this.skillName.setAlignment(Align.center);
        this.skillName.setColor(Color.BLACK);
        this.skillDescription = new Label("", skin);
        this.skillDescription.setAlignment(Align.center | Align.right);
        //this.skillDescription.setFontScale(2.5f);
        this.skillDescription.setColor(Color.BLACK);
        this.font = skin.getFont("font");
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        Components.add(new SkillLevelDrawer(iconFactory.getAsset(1)));
        Components.add(new SkillManaCostDrawer(iconFactory.getAsset(1)));
        Components.add(new SkillTypeDrawer(iconFactory.getAsset(1)));
        init();
    }

    private void init() {
        padTop(32);
        getStyle().titleFont.getData().setScale(2);
        setFillParent(false);
        setMovable(false);
        setResizable(false);
        setVisible(false);
        setModal(true);

        add(skillName).padTop(SKILL_NAME_PADDING).width(800).row();
        Stack stack = new Stack();
        stack.add(skillIcon);
        stack.add(skillFrame);
        add(stack).padTop(SKILL_ICON_PADDING).row();
        add(skillDescription).padTop(SKILL_DESCRIPTION_PADDING).height(400).row();
    }

    /*
     * If overriding this method, you should call pack() afterwards
     */
    @Override
    public Actor build() {
        pack();
        return this;
    }

    void hide() {
        setVisible(false);
        currentSkill = null;
    }

    public void open(Skill skill) {
        currentSkill = skill;
        open(skill.getIconID(), skill.getName(), skill.getRarity(), getDescription(skill));
    }

    private void open(int skillID, String skillName, Rarity skillRarity, String skillDescription) {
        skillIcon.setDrawable(new TextureRegionDrawable(iconFactory.getAsset(skillID)));
        skillFrame.setDrawable(new TextureRegionDrawable(frameFactory.getAsset(skillRarity)));
        this.skillName.setText(skillName);
        setSkillDescription(skillDescription);
        setPosition((SCREEN_WIDTH() - getWidth()) * 0.5f,
                (SCREEN_HEIGHT() - getHeight()) * 0.5f);
        pack();
        setVisible(true);
    }

    void setSkillDescription(String description) {
        skillDescription.setText(description);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Vector2 iconPosition = skillIcon.localToStageCoordinates(new Vector2(0, 0));
        float iconWidth = skillIcon.getWidth();
        float iconHeight = skillIcon.getHeight();

        Vector2 initialFontScale = new Vector2(font.getScaleX(), font.getScaleY());

        for (ListItemDrawer<? super ReadableSkill> component : Components) {
            component.draw(batch, font, 0, currentSkill, iconPosition.x, iconPosition.y, iconWidth, iconHeight);
        }

        font.getData().setScale(initialFontScale.x, initialFontScale.y);
    }

    static String getDescription(Skill skill) {
        StringBuilder result = new StringBuilder();
        for (SkillCharacteristic sc : skill.getCharacteristics()) {
            result.append(getDescription(sc, skill.getLevel()));
            result.append('\n');
        }
        return result.toString();
    }

    private static String getDescription(SkillCharacteristic sc, int skillLevel) {
        String result = getDescription(sc.getTarget());
        result += " ";
        result += String.valueOf(sc.getCharacteristic());
        result += (sc.getChangeType() == ValueChangeType.DECREASE) ? " -" : " +";
        result += String.valueOf(sc.getValue(skillLevel));
        return result;
    }

    private static String getDescription(int skillCharTarget) {
        if (skillCharTarget == 0) return "SELF";
        if (skillCharTarget > 0) return "ALLIES("+skillCharTarget+")";
        return "ENEMIES("+(-skillCharTarget)+")";
    }

}
