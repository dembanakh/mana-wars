package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.TimeoutSelection;

import io.reactivex.functions.Consumer;

public class ApplicableSkillsList2D<T extends ActiveSkill> extends ClickableSkillsList2D<T>
        implements BlockableSkillsList<T> {

    private final TimeoutSelection<Integer> blockedSkills;

    public ApplicableSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                                  AssetFactory<Rarity, TextureRegion> frameFactory,
                                  Consumer<? super Integer> onSkillClick) {
        super(skin, cols, iconFactory, frameFactory);
        this.blockedSkills = new TimeoutSelection<>();

        setOnSkillClick(onSkillClick);
    }

    @Override
    protected boolean isClickable(int index) {
        return super.isClickable(index) && !blockedSkills.contains(index);
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        T appliedSkill = getItem(appliedSkillIndex);
        for (int i = 0; i < items.size; ++i) {
            if (i == appliedSkillIndex)
                blockedSkills.selectFor(i, appliedSkill.getCastTime() + appliedSkill.getCooldown());
            else
                blockedSkills.selectFor(i, appliedSkill.getCastTime());
        }
    }

    @Override
    public Actor toActor() {
        return this;
    }

    @Override
    public void update(float delta) {
        blockedSkills.update(delta);
    }

}
