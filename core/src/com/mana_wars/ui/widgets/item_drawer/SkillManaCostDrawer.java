package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ReadableSkill;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class SkillManaCostDrawer implements ListItemDrawer<ReadableSkill> {

    private TextureRegion region;
    private ShapeDrawer drawer;

    public SkillManaCostDrawer(TextureRegion placeholderRegion) {
        this.region = placeholderRegion;
    }

    @Override
    public void draw(Batch batch, BitmapFont font, int index, ReadableSkill item, float x, float y, float width, float height) {
        if (!shouldShowManaCost(item)) return;

        String manaCost = String.valueOf(item.getManaCost());
        font.getData().setScale(3);
        float halfLineHeight = font.getLineHeight() / 2;

        if (drawer == null || drawer.getBatch() != batch) drawer = new ShapeDrawer(batch, region);

        drawer.setColor(Color.WHITE);
        drawer.filledRectangle(x + width - halfLineHeight, y - halfLineHeight,
                2 * halfLineHeight, 2 * halfLineHeight);

        font.setColor(Color.BLUE);
        font.draw(batch, manaCost, x, y + halfLineHeight,
                0, manaCost.length(), 2 * width, Align.center,
                false, "");
    }

    private boolean shouldShowManaCost(ReadableSkill skill) {
        return skill.getRarity() != Rarity.EMPTY;
    }
}
