package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.animation.controller.SkillIconAnimationController;
import com.mana_wars.ui.animation.controller.UIAnimationController;
import com.mana_wars.ui.widgets.base.ClickableList2D;
import com.mana_wars.ui.widgets.base.ListItemConsumer;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

import java.util.Arrays;
import java.util.Collections;

public class ApplicableSkillsList2D<T extends ActiveSkill> implements BlockableSkillsList<T> {

    private final ClickableList2D<T> list;

    private final UIAnimationController<Integer, SkillIconAnimationController.Type> animationController;
    private final BitmapFont cooldownFont;

    private int castTime, cooldown;

    public ApplicableSkillsList2D(Skin skin, ListItemDrawer<? super T> listItemDrawer, int cols,
                                  ListItemConsumer<? super T> onSkillClick,
                                  UIAnimationController<Integer, SkillIconAnimationController.Type> animationController,
                                  String cooldownFontName) {
        this.list = new ClickableList2D<T>(skin, listItemDrawer, cols, onSkillClick) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                animationController.initBatch(batch, cooldownFont);
                super.draw(batch, parentAlpha);
            }
        };
        this.animationController = animationController;
        this.cooldownFont = skin.getFont(cooldownFontName);
    }

    @Override
    public void setItems(Iterable<? extends T> newItems) {
        list.setItems(newItems);
        animationController.clear();

        int i = 0;
        for (T skill : list.getItemsCopy()) {
            if (skill.getRarity() == Rarity.EMPTY) {
                animationController.add(i, Collections.emptyList());
            }
            i++;
        }
    }

    @Override
    public void setDurationCoefficients(int castTime, int cooldown) {
        this.castTime = castTime;
        this.cooldown = cooldown;
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        T appliedSkill = list.getItem(appliedSkillIndex);
        if (appliedSkill.getRarity() != Rarity.EMPTY) {
            int i = 0;
            for (T skill : list.getItemsCopy()) {
                if (skill.getRarity() == Rarity.EMPTY) continue;
                if (i == appliedSkillIndex)
                    animationController.add(i,
                            Arrays.asList(
                                    new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.CAST_APPLIED,
                                            appliedSkill.getCastTime(castTime)),
                                    new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.COOLDOWN,
                                            appliedSkill.getCooldown(cooldown))));
                else
                    animationController.add(i,
                            Arrays.asList(
                                    new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.CAST_NON_APPLIED,
                                            appliedSkill.getCastTime(castTime))));
                i++;
            }
        }
    }

    @Override
    public void update(float delta) {
        animationController.update(delta);
    }

    @Override
    public Actor build() {
        return list;
    }
}
