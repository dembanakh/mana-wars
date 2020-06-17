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

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;

    public StaticSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                              AssetFactory<Rarity, TextureRegion> frameFactory) {
        this(skin.get(List.ListStyle.class), cols, iconFactory, frameFactory);
    }

    protected StaticSkillsList2D(List.ListStyle style, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
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
        String level = String.valueOf(item.getLevel());
        String manaCost = String.valueOf(item.getManaCost());

        float iconOffsetX = (width - icon.getRegionWidth()) / 2;
        float iconOffsetY = (height - icon.getRegionHeight()) / 2;
        float frameOffsetX = (width - frame.getRegionWidth()) / 2;
        float frameOffsetY = (height - frame.getRegionHeight()) / 2;

        batch.draw(icon, x + iconOffsetX, y + iconOffsetY);
        batch.draw(frame, x + frameOffsetX, y + frameOffsetY);

        if (shouldShowLevel(item)) {
            font.getData().setScale(3);
            font.setColor(Color.BLACK);
            font.draw(batch, level, x, y + iconOffsetY + font.getLineHeight() / 2,
                    0, level.length(), 2 * iconOffsetX, Align.center,
                    false, "");
            font.setColor(Color.BLUE);
            font.draw(batch, manaCost, x + width - 2 * iconOffsetX, y + iconOffsetY + font.getLineHeight() / 2,
                    0, manaCost.length(), 2 * iconOffsetX, Align.center,
                    false, "");
        }
    }

    protected boolean shouldShowLevel(T item) {
        return item.getRarity() != Rarity.EMPTY;
    }

}
