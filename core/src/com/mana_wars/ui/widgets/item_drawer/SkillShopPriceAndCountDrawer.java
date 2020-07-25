package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class SkillShopPriceAndCountDrawer implements ListItemDrawer<ShopSkill> {

    private final TextureRegion region;
    private ShapeDrawer drawer;

    public SkillShopPriceAndCountDrawer(TextureRegion placeholderRegion) {
        this.region = placeholderRegion;
    }

    @Override
    public void draw(Batch batch, BitmapFont font, int index, ShopSkill item, float x, float y, float width, float height) {
        if (!item.canBePurchased()) return;

        String price = String.valueOf(item.getPrice());

        font.getData().setScale(3);
        float halfLineHeight = font.getLineHeight() / 2;
        font.setColor(Color.BLUE);
        font.draw(batch, price, x, y - halfLineHeight,
                0, price.length(), width, Align.center,
                false, "");

        String count = String.valueOf(item.instancesLeft());

        if (drawer == null || drawer.getBatch() != batch) drawer = new ShapeDrawer(batch, region);

        drawer.setColor(Color.WHITE);
        drawer.filledRectangle(x - halfLineHeight, y + height - halfLineHeight,
                2 * halfLineHeight, 2 * halfLineHeight);

        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f);
        halfLineHeight = font.getLineHeight() / 2;
        font.draw(batch, count, x - width, y + height + halfLineHeight,
                0, count.length(), 2 * width, Align.center,
                false, "");
    }
}
