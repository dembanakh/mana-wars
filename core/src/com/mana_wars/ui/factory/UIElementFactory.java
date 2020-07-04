package com.mana_wars.ui.factory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.animation.controller.SkillIconAnimationController;
import com.mana_wars.ui.widgets.base.ClickableList2D;
import com.mana_wars.ui.widgets.base.List2D;
import com.mana_wars.ui.widgets.base.ListItemConsumer;
import com.mana_wars.ui.widgets.item_drawer.ApplicableSkillDrawer;
import com.mana_wars.ui.widgets.item_drawer.StandardSkillDrawer;
import com.mana_wars.ui.widgets.skills_list_2d.ApplicableSkillsList2D;
import com.mana_wars.ui.widgets.skills_list_2d.OperationSkillsList2D;

public final class UIElementFactory {

    public static TextButton getButton(TextButton.TextButtonStyle style, String label, ChangeListener eventListener) {
        TextButton button = new TextButton(label, style);
        button.addListener(eventListener);
        return button;
    }

    public static TextButton getButton(Skin skin, String label, ChangeListener eventListener) {
        return getButton(skin.get(TextButton.TextButtonStyle.class), label, eventListener);
    }

    public static Label.LabelStyle emptyLabelStyle() {
        return new Label.LabelStyle(new BitmapFont(), new Color());
    }

    public static List.ListStyle emptyListStyle() {
        return new List.ListStyle();
    }

    private static List.ListStyle emptyListStyle(Skin skin) {
        List.ListStyle style = new List.ListStyle(skin.get(List.ListStyle.class));
        style.background = new BaseDrawable(style.background);
        return style;
    }

    public static List2D<Skill> orderedOperationSkillsList(Skin skin, int cols,
                                                    AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                    AssetFactory<Rarity, TextureRegion> rarityFrameFactory) {
        return new OperationSkillsList2D(emptyListStyle(skin),
                new StandardSkillDrawer<>(skillIconFactory, rarityFrameFactory),
                cols, true);
    }

    public static List2D<Skill> unorderedOperationSkillsList(Skin skin, int cols,
                                                           AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                           AssetFactory<Rarity, TextureRegion> rarityFrameFactory) {
        return new OperationSkillsList2D(emptyListStyle(skin),
                new StandardSkillDrawer<>(skillIconFactory, rarityFrameFactory),
                cols, false);
    }

    public static ApplicableSkillsList2D<ActiveSkill> applicableSkillsList(Skin skin, int cols,
                                                                          AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                                          AssetFactory<Rarity, TextureRegion> rarityFrameFactory,
                                                                          AssetFactory<String, Texture> imageFactory,
                                                                          ListItemConsumer<ActiveSkill> onSkillClick) {
        SkillIconAnimationController animationController =
                new SkillIconAnimationController(
                        new TextureRegion(imageFactory.getAsset("white")),
                        skin.getFont("font"));
        return new ApplicableSkillsList2D<>(skin,
                new ApplicableSkillDrawer<>(skillIconFactory, rarityFrameFactory, animationController),
                cols, onSkillClick, animationController);
    }

    public static <T extends Skill> List2D<T> skillsListWithLevel(int cols,
                                                             AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                             AssetFactory<Rarity, TextureRegion> rarityFrameFactory) {
        return new List2D<>(emptyListStyle(),
                new StandardSkillDrawer<>(skillIconFactory, rarityFrameFactory),
                cols);
    }

    public static <T extends Skill> List2D<T> skillsListWithoutLevel(int cols,
                                                                AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                                AssetFactory<Rarity, TextureRegion> rarityFrameFactory) {
        return new List2D<>(emptyListStyle(),
                new StandardSkillDrawer<T>(skillIconFactory, rarityFrameFactory) {
                    @Override
                    protected boolean shouldShowLevel(Skill skill) {
                        return false;
                    }
                },
                cols);
    }

    public static <T extends Skill> List2D<T> clickableSkillsListWithLevel(Skin skin, int cols,
                                                                AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                                AssetFactory<Rarity, TextureRegion> rarityFrameFactory,
                                                                ListItemConsumer<Skill> onSkillClick) {
        return new ClickableList2D<>(skin,
                new StandardSkillDrawer<>(skillIconFactory, rarityFrameFactory),
                cols, onSkillClick);
    }

    public static <T extends Skill> List2D<T> clickableSkillsListWithoutLevel(Skin skin, int cols,
                                                    AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                    AssetFactory<Rarity, TextureRegion> rarityFrameFactory,
                                                    ListItemConsumer<Skill> onSkillClick) {
        return new ClickableList2D<>(skin,
                new StandardSkillDrawer<T>(skillIconFactory, rarityFrameFactory) {
                    @Override
                    protected boolean shouldShowLevel(Skill skill) {
                        return false;
                    }
                },
                cols, onSkillClick);
    }


    private UIElementFactory() {
    }

}
