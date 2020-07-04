package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.Batch;
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

public class ApplicableSkillsList2D<T extends ActiveSkill> extends ClickableList2D<T>
        implements BlockableSkillsList<T> {

    private final UIAnimationController<Integer, SkillIconAnimationController.Type> animationController;

    public ApplicableSkillsList2D(Skin skin, ListItemDrawer<? super T> listItemDrawer, int cols,
                                     ListItemConsumer<? super T> onSkillClick,
                                     UIAnimationController<Integer, SkillIconAnimationController.Type> animationController) {
        super(skin, listItemDrawer, cols, onSkillClick);
        this.animationController = animationController;
    }

    /*
    protected boolean isClickable(int index) {
        return super.isClickable(index) && !animationController.contains(index);
    }*/

    @Override
    public void setItems(Iterable<? extends T> newItems) {
        super.setItems(newItems);
        animationController.clear();

        forEachItem((skill, index) -> {
            if (skill.getRarity() == Rarity.EMPTY) {
                animationController.add(index, Collections.emptyList());
            }
        });
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        T appliedSkill = getItem(appliedSkillIndex);
        forEachItem((skill, index) -> {
            if (skill.getRarity() == Rarity.EMPTY) return;
            if (index == appliedSkillIndex)
                animationController.add(index,
                        Arrays.asList(
                                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.CAST_APPLIED,
                                        appliedSkill.getCastTime()),
                                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.COOLDOWN,
                                        appliedSkill.getCooldown())));
            else
                animationController.add(index,
                        Arrays.asList(
                                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.CAST_NON_APPLIED,
                                        appliedSkill.getCastTime())));
        });
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

}
