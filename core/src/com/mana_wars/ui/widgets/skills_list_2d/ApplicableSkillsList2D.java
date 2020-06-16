package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.animation.controller.SkillIconAnimationController;
import com.mana_wars.ui.animation.controller.UIAnimationController;
import com.mana_wars.ui.factory.AssetFactory;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.functions.Consumer;

public class ApplicableSkillsList2D<T extends ActiveSkill> extends ClickableSkillsList2D<T>
        implements BlockableSkillsList<T> {

    private final UIAnimationController<Integer, SkillIconAnimationController.Type> animationController;

    protected ApplicableSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                                     AssetFactory<Rarity, TextureRegion> frameFactory,
                                     Consumer<? super Integer> onSkillClick) {
        super(skin, cols, iconFactory, frameFactory);
        this.animationController = new SkillIconAnimationController(iconFactory.getAsset(1),
                getStyle().font);

        setOnSkillClick(onSkillClick);
    }

    @Override
    protected boolean isClickable(int index) {
        return super.isClickable(index) && !animationController.contains(index);
    }

    @Override
    public void setItems(Iterable<? extends T> newItems) {
        super.setItems(newItems);
        animationController.clear();
        for (int i = 0; i < items.size; ++i) {
            if (getItem(i).getRarity() == Rarity.EMPTY) {
                animationController.add(i, Collections.emptyList());
            }
        }
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        T appliedSkill = getItem(appliedSkillIndex);
        for (int i = 0; i < items.size; ++i) {
            if (getItem(i).getRarity() == Rarity.EMPTY) continue;
            if (i == appliedSkillIndex)
                animationController.add(i,
                        Arrays.asList(
                                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.CAST_APPLIED,
                                                        appliedSkill.getCastTime()),
                                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.COOLDOWN,
                                                        appliedSkill.getCooldown())));
            else
                animationController.add(i,
                        Arrays.asList(
                                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.CAST_NON_APPLIED,
                                        appliedSkill.getCastTime())));
        }
    }

    @Override
    public Actor toActor() {
        return this;
    }

    @Override
    public void update(float delta) {
        animationController.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        animationController.initBatch(batch);
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void drawItem(Batch batch, BitmapFont font, int index, T item,
                            float x, float y, float width, float height) {
        super.drawItem(batch, font, index, item, x, y, width, height);
        animationController.animate(index, x, y, width, height);
    }
}
