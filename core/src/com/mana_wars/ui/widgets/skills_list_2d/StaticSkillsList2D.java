package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.factory.AssetFactory;

public class StaticSkillsList2D<T extends Skill> extends List2D<T> {

    final AssetFactory<Integer, TextureRegion> iconFactory;
    final AssetFactory<Rarity, TextureRegion> frameFactory;

    public StaticSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                        AssetFactory<Rarity, TextureRegion> frameFactory) {
        this(skin.get(List.ListStyle.class), cols, iconFactory, frameFactory);
    }

    public StaticSkillsList2D(List.ListStyle style, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                        AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(style, cols);
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        this.selection.setDisabled(true);
    }

    @Override
    protected void drawItem(Batch batch, BitmapFont font, int index, T item,
                            float x, float y, float width, float height) {
        TextureRegion icon = iconFactory.getAsset(item.getIconID());
        TextureRegion frame = frameFactory.getAsset(item.getRarity());
        String text = String.valueOf(item.getLevel());

        float iconOffsetX = (width - icon.getRegionWidth()) / 2;
        float iconOffsetY = (height - icon.getRegionHeight()) / 2;
        float frameOffsetX = (width - frame.getRegionWidth()) / 2;
        float frameOffsetY = (height - frame.getRegionHeight()) / 2;

        batch.draw(icon, x + iconOffsetX, y + iconOffsetY);
        batch.draw(frame, x + frameOffsetX, y + frameOffsetY);

        if (shouldShowLevel(item)) {
            font.setColor(Color.BLACK);
            font.getData().setScale(2);
            font.draw(batch, text, x, y + frameOffsetY, 0, text.length(),
                    width, Align.center, false, "");
        }
    }
    
    protected boolean shouldShowLevel(T item) {
        return item.getRarity() != Rarity.EMPTY;
    }

}
