package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.factory.AssetFactory;

public class ApplicableSkillsList2D extends ClickableSkillsList2D<ActiveSkill> {

    public ApplicableSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory, AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(skin, cols, iconFactory, frameFactory);
        this.selection.setDisabled(false);
        this.selection.clear();
    }

    @Override
    public ActiveSkill getItem(int index) {
        if (selection.contains(items.get(index))) return null;
        return super.getItem(index);
    }
}
