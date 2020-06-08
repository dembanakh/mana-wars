package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.TimeoutSelection;

import io.reactivex.functions.Consumer;

public class ApplicableSkillsList2D<T extends Skill> extends ClickableSkillsList2D<T>
        implements BlockableSkillsList<T> {

    private final TimeoutSelection<Integer> blockedSkills;

    public ApplicableSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                                  AssetFactory<Rarity, TextureRegion> frameFactory,
                                  Consumer<? super T> onSkillClick) {
        super(skin, cols, iconFactory, frameFactory);
        this.blockedSkills = new TimeoutSelection<>();

        setOnSkillClick(onSkillClick);
    }

    @Override
    protected boolean isClickable(int index) {
        return super.isClickable(index) && !blockedSkills.contains(index);
    }

    @Override
    public void blockSkillAtFor(int index, float time) {
        blockedSkills.selectFor(index, time);
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
