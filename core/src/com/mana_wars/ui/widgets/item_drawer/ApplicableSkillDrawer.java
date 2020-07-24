package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.animation.controller.UIAnimator;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

public class ApplicableSkillDrawer<T extends ActiveSkill> extends StandardSkillDrawer<T> {

    private final UIAnimator<Integer> animator;

    @SafeVarargs
    public ApplicableSkillDrawer(AssetFactory<Integer, TextureRegion> iconFactory,
                                 AssetFactory<Rarity, TextureRegion> frameFactory,
                                 UIAnimator<Integer> animator,
                                 ListItemDrawer<? super T>... skillComponents) {
        super(iconFactory, frameFactory, skillComponents);
        this.animator = animator;
    }

    @Override
    public void draw(Batch batch, BitmapFont font, int index, T item,
                            float x, float y, float width, float height) {
        super.draw(batch, font, index, item, x, y, width, height);
        animator.animate(index, x, y, width, height);
    }
}
