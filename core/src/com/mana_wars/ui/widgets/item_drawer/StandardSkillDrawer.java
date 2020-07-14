package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

public class StandardSkillDrawer<T extends Skill> implements ListItemDrawer<T> {

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;
    private final ListItemDrawer<Skill>[] skillComponents;

    @SafeVarargs
    public StandardSkillDrawer(AssetFactory<Integer, TextureRegion> iconFactory,
                               AssetFactory<Rarity, TextureRegion> frameFactory,
                               ListItemDrawer<Skill>... skillComponents) {
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        this.skillComponents = skillComponents;
    }

    @Override
    public void draw(Batch batch, BitmapFont font, int index, T item, float x, float y, float width, float height) {
        TextureRegion icon = iconFactory.getAsset(item.getIconID());
        TextureRegion frame = frameFactory.getAsset(item.getRarity());

        float iconOffsetX = (width - icon.getRegionWidth()) / 2;
        float iconOffsetY = (height - icon.getRegionHeight()) / 2;
        float frameOffsetX = (width - frame.getRegionWidth()) / 2;
        float frameOffsetY = (height - frame.getRegionHeight()) / 2;

        batch.draw(icon, x + iconOffsetX, y + iconOffsetY);
        batch.draw(frame, x + frameOffsetX, y + frameOffsetY);

        for (ListItemDrawer<Skill> component : skillComponents) {
            component.draw(batch, font, index, item, x + iconOffsetX, y + iconOffsetY,
                    icon.getRegionWidth(), icon.getRegionHeight());
        }
    }
}
