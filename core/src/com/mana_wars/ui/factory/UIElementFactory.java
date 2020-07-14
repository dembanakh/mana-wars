package com.mana_wars.ui.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.mana_wars.ui.widgets.item_drawer.SkillLevelDrawer;
import com.mana_wars.ui.widgets.item_drawer.SkillManaCostDrawer;
import com.mana_wars.ui.widgets.item_drawer.SkillTypeDrawer;
import com.mana_wars.ui.widgets.item_drawer.StandardSkillDrawer;
import com.mana_wars.ui.widgets.skills_list_2d.ApplicableSkillsList2D;
import com.mana_wars.ui.widgets.skills_list_2d.OperationSkillsList2D;

public final class UIElementFactory {

    public static TextButton getButton(Skin skin, String label, ChangeListener eventListener) {
        TextButton button = new TextButton(label, skin);
        button.addListener(eventListener);
        return button;
    }

    public static TextButton getButton(String label, ChangeListener eventListener) {
        TextButton button = new TextButton(label, new TextButton.TextButtonStyle());
        button.addListener(eventListener);
        return button;
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
                new StandardSkillDrawer<>(skillIconFactory, rarityFrameFactory,
                        new SkillLevelDrawer(skillIconFactory.getAsset(1)),
                        new SkillManaCostDrawer(skillIconFactory.getAsset(1)),
                        new SkillTypeDrawer(skillIconFactory.getAsset(1))),
                cols, true);
    }

    public static List2D<Skill> unorderedOperationSkillsList(Skin skin, int cols,
                                                           AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                           AssetFactory<Rarity, TextureRegion> rarityFrameFactory) {
        return new OperationSkillsList2D(skin,
                new StandardSkillDrawer<>(skillIconFactory, rarityFrameFactory,
                        new SkillLevelDrawer(skillIconFactory.getAsset(1)),
                        new SkillManaCostDrawer(skillIconFactory.getAsset(1)),
                        new SkillTypeDrawer(skillIconFactory.getAsset(1))),
                cols, false);
    }

    public static ApplicableSkillsList2D<ActiveSkill> applicableSkillsList(Skin skin, int cols,
                                                                          AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                                          AssetFactory<Rarity, TextureRegion> rarityFrameFactory,
                                                                          AssetFactory<String, TextureRegion> imageFactory,
                                                                          ListItemConsumer<ActiveSkill> onSkillClick) {
        SkillIconAnimationController animationController =
                new SkillIconAnimationController(
                        new TextureRegion(imageFactory.getAsset("white")));
        return new ApplicableSkillsList2D<>(skin,
                new ApplicableSkillDrawer<>(skillIconFactory, rarityFrameFactory, animationController),
                cols, onSkillClick, animationController, "font");
    }

    public static <T extends Skill> List2D<T> skillsListWithoutLevel(Skin skin, int cols,
                                                                AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                                AssetFactory<Rarity, TextureRegion> rarityFrameFactory) {
        return new List2D<>(emptyListStyle(skin),
                new StandardSkillDrawer<T>(skillIconFactory, rarityFrameFactory),
                cols);
    }

    public static <T extends Skill> List2D<T> clickableSkillsListWithoutLevel(Skin skin, int cols,
                                                    AssetFactory<Integer, TextureRegion> skillIconFactory,
                                                    AssetFactory<Rarity, TextureRegion> rarityFrameFactory,
                                                    ListItemConsumer<Skill> onSkillClick) {
        return new ClickableList2D<>(skin,
                new StandardSkillDrawer<T>(skillIconFactory, rarityFrameFactory,
                        new SkillTypeDrawer(skillIconFactory.getAsset(1))),
                cols, onSkillClick);
    }


    private UIElementFactory() {
    }
}
