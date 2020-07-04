package com.mana_wars.ui.widgets.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public interface ListItemDrawer<T> {
    void draw(Batch batch, BitmapFont font, int index, T item,
              float x, float y, float width, float height);
}
