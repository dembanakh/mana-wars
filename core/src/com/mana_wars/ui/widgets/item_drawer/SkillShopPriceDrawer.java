package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.skills.ShopSkill;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

public class SkillShopPriceDrawer implements ListItemDrawer<ShopSkill> {
    @Override
    public void draw(Batch batch, BitmapFont font, int index, ShopSkill item, float x, float y, float width, float height) {
        if (item.isPurchased()) return;

        String price = String.valueOf(item.getPrice());

        font.getData().setScale(3);
        float halfLineHeight = font.getLineHeight() / 2;
        font.setColor(Color.BLUE);
        font.draw(batch, price, x, y - halfLineHeight,
                0, price.length(), width, Align.center,
                false, "");
    }
}
