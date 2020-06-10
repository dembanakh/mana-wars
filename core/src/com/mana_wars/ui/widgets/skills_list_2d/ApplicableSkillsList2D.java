package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.animation.SkillTimeoutAnimation;
import com.mana_wars.ui.animation.UIAnimation;
import com.mana_wars.ui.factory.AssetFactory;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.functions.Consumer;

public class ApplicableSkillsList2D<T extends ActiveSkill> extends ClickableSkillsList2D<T>
        implements BlockableSkillsList<T> {

    private final UIAnimation<Integer, SkillTimeoutAnimation.Type> animation;

    public ApplicableSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                                  AssetFactory<Rarity, TextureRegion> frameFactory,
                                  Consumer<? super Integer> onSkillClick) {
        super(skin, cols, iconFactory, frameFactory);
        this.animation = new SkillTimeoutAnimation(iconFactory.getAsset(1));

        setOnSkillClick(onSkillClick);
    }

    @Override
    protected boolean isClickable(int index) {
        return super.isClickable(index) && !animation.contains(index);
    }

    @Override
    public void setItems(Iterable<? extends T> newItems) {
        super.setItems(newItems);
        for (int i = 0; i < items.size; ++i) {
            if (getItem(i).getRarity() == Rarity.EMPTY) {
                animation.add(i, Collections.emptyList());
            }
        }
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        T appliedSkill = getItem(appliedSkillIndex);
        for (int i = 0; i < items.size; ++i) {
            if (getItem(i).getRarity() == Rarity.EMPTY) continue;
            if (i == appliedSkillIndex)
                animation.add(i,
                        Arrays.asList(
                                new UIAnimation.KeyFrame<>(SkillTimeoutAnimation.Type.CAST_APPLIED,
                                                        appliedSkill.getCastTime()),
                                new UIAnimation.KeyFrame<>(SkillTimeoutAnimation.Type.COOLDOWN,
                                                        appliedSkill.getCooldown())));
            else
                animation.add(i,
                        Arrays.asList(
                                new UIAnimation.KeyFrame<>(SkillTimeoutAnimation.Type.CAST_NON_APPLIED,
                                        appliedSkill.getCastTime())));
        }
    }

    @Override
    public Actor toActor() {
        return this;
    }

    @Override
    public void update(float delta) {
        animation.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        animation.initBatch(batch);
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void drawItem(Batch batch, BitmapFont font, int index, T item,
                            float x, float y, float width, float height) {
        super.drawItem(batch, font, index, item, x, y, width, height);
        animation.animate(index, x, y, width, height);
    }
}
