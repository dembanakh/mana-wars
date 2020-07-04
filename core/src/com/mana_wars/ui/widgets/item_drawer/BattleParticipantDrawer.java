package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

public class BattleParticipantDrawer<T> implements ListItemDrawer<T> {
    @Override
    public void draw(Batch batch, BitmapFont font, int index, T item, float x, float y, float width, float height) {
        String string = item.toString();
        font.draw(batch, string, x, y, 0, string.length(), width, Align.center, false, "...");
    }
}
