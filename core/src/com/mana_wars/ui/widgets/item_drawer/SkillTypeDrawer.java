package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class SkillTypeDrawer implements ListItemDrawer<Skill> {

    private TextureRegion region;
    private ShapeDrawer drawer;

    public SkillTypeDrawer(TextureRegion placeholderRegion) {
        this.region = placeholderRegion;
    }

    @Override
    public void draw(Batch batch, BitmapFont font, int index, Skill item, float x, float y, float width, float height) {
        if (!shouldShowType(item)) return;

        String type = (item instanceof ActiveSkill) ? "A" : "P";

        font.getData().setScale(3);
        float halfLineHeight = font.getLineHeight() / 2;

        if (drawer == null || drawer.getBatch() != batch) drawer = new ShapeDrawer(batch, region);

        drawer.setColor(Color.WHITE);
        drawer.filledRectangle(x + width - halfLineHeight, y + height - halfLineHeight,
                2 * halfLineHeight, 2 * halfLineHeight);

        font.setColor(Color.FOREST);
        font.draw(batch, type, x, y + height + halfLineHeight,
                0, 1, 2 * width, Align.center,
                false, "");
    }

    private boolean shouldShowType(Skill skill) {
        return skill.getRarity() != Rarity.EMPTY;
    }
}
