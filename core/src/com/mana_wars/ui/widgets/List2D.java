package com.mana_wars.ui.widgets;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.mana_wars.model.entity.base.GameItem;
import com.mana_wars.ui.factory.AssetFactory;

/**
 * Texture-oriented 2-dimensional list based on the original List implementation in Libgdx.
 * @param <T>
 */
public abstract class List2D<T extends GameItem> extends Widget implements Cullable {

    List.ListStyle style;
    final Array<List2DItem<T>> items = new Array<>();
    Selection<List2DItem<T>> selection = new Selection<>();
    private Rectangle cullingArea;
    private float prefWidth, prefHeight;
    float itemHeight, itemWidth;
    protected int alignment = Align.left;
    int pressedIndex = -1, overIndex = -1;
    private InputListener keyListener;
    boolean typeToSelect;

    private int cols;
    protected AssetFactory<Integer, TextureRegion> textureFactory;

    public List2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> textureFactory) {
        this(skin.get(List.ListStyle.class), cols, textureFactory);
    }

    public List2D(Skin skin, String styleName, int cols, AssetFactory<Integer, TextureRegion> textureFactory) {
        this(skin.get(styleName, List.ListStyle.class), cols, textureFactory);
    }

    public List2D(List.ListStyle style, int cols, AssetFactory<Integer, TextureRegion> textureFactory) {
        this.textureFactory = textureFactory;
        selection.setActor(this);
        selection.setRequired(true);

        setCols(cols);
        setStyle(style);
        setSize(getPrefWidth(), getPrefHeight());

        addListener(keyListener = new InputListener() {
            public boolean keyDown (InputEvent event, int keycode) {
                if (items.isEmpty()) return false;
                int index;
                switch (keycode) {
                    case Input.Keys.A:
                        if (UIUtils.ctrl() && selection.getMultiple()) {
                            selection.clear();
                            selection.addAll(items);
                            return true;
                        }
                        break;
                    case Input.Keys.HOME:
                        setSelectedIndex(0);
                        return true;
                    case Input.Keys.END:
                        setSelectedIndex(items.size - 1);
                        return true;
                    case Input.Keys.DOWN:
                        index = items.indexOf(getSelected(), false) + cols;
                        if (index >= items.size) index -= items.size;
                        setSelectedIndex(index);
                        return true;
                    case Input.Keys.UP:
                        index = items.indexOf(getSelected(), false) - cols;
                        if (index < 0) index += items.size;
                        setSelectedIndex(index);
                        return true;
                    case Input.Keys.LEFT:
                        index = items.indexOf(getSelected(), false) - 1;
                        if (index < 0) index = items.size - 1;
                        setSelectedIndex(index);
                        return true;
                    case Input.Keys.RIGHT:
                        index = items.indexOf(getSelected(), false) + 1;
                        if (index >= items.size) index = 0;
                        setSelectedIndex(index);
                        return true;
                    case Input.Keys.ESCAPE:
                        if (getStage() != null) getStage().setKeyboardFocus(null);
                        return true;
                }
                return false;
            }

            public boolean keyTyped (InputEvent event, char character) {
                return false;
            }
        });

        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return true;
                if (selection.isDisabled()) return true;
                if (getStage() != null) getStage().setKeyboardFocus(List2D.this);
                if (items.size == 0) return true;
                int index = getItemIndexAt(x, y);
                if (index == -1) return true;
                if (selection.contains(items.get(index)))
                    selection.remove(items.get(index));
                else
                    selection.choose(items.get(index));
                pressedIndex = index;
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return;
                pressedIndex = -1;
            }

            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                overIndex = getItemIndexAt(x, y);
            }

            public boolean mouseMoved (InputEvent event, float x, float y) {
                overIndex = getItemIndexAt(x, y);
                return false;
            }

            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == 0) pressedIndex = -1;
                if (pointer == -1) overIndex = -1;
            }
        });
    }

    public void layout () {
        Drawable selectedDrawable = style.selection;
        Drawable background = style.background;

        float containerWidth = getWidth() - selectedDrawable.getRightWidth() - selectedDrawable.getLeftWidth();
        if (background != null) {
            containerWidth -= background.getLeftWidth() + background.getRightWidth();
        }
        itemHeight = itemWidth = containerWidth / cols;

        prefWidth = getWidth();
        prefHeight = (float)(Math.ceil((float)items.size / cols) * itemHeight);

        if (background != null) {
            prefHeight += background.getTopHeight() + background.getBottomHeight();
        }
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        validate();

        drawBackground(batch, parentAlpha);

        BitmapFont font = style.font;
        Drawable selectedDrawable = style.selection;
        Color fontColorSelected = style.fontColorSelected;
        Color fontColorUnselected = style.fontColorUnselected;

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        float x = getX(), y = getY(), width = getWidth(), height = getHeight();
        float itemX = 0;
        float itemY = height;

        Drawable background = style.background;
        if (background != null) {
            float leftWidth = background.getLeftWidth();
            x += leftWidth;
            itemY -= background.getTopHeight();
            width -= leftWidth + background.getRightWidth();
        }

        font.setColor(fontColorUnselected.r, fontColorUnselected.g, fontColorUnselected.b, fontColorUnselected.a * parentAlpha);
        for (int i = 0; i < items.size; i += cols) {
            for (int j = i; j < i + 5 && j < items.size; ++j) {
                if (cullingArea == null || (itemY - itemHeight <= cullingArea.y + cullingArea.height && itemY >= cullingArea.y)) {
                    List2DItem<T> item = items.get(j);
                    boolean selected = selection.contains(item);
                    Drawable drawable = null;
                    if (pressedIndex == j && style.down != null)
                        drawable = style.down;
                    else if (selected) {
                        drawable = selectedDrawable;
                        font.setColor(fontColorSelected.r, fontColorSelected.g, fontColorSelected.b, fontColorSelected.a * parentAlpha);
                    } else if (overIndex == j && style.over != null) //
                        drawable = style.over;
                    if (drawable != null) drawable.draw(batch, x + itemX, y + itemY - itemHeight, itemWidth, itemHeight);
                    drawItem(batch, font, item.index, item.data, x + itemX, y + itemY - itemHeight, itemWidth, itemHeight);
                    if (selected) {
                        font.setColor(fontColorUnselected.r, fontColorUnselected.g, fontColorUnselected.b,
                                fontColorUnselected.a * parentAlpha);
                    }
                } else if (itemY < cullingArea.y) {
                    break;
                }
                itemX += itemWidth;
            }
            itemX = 0;
            itemY -= itemHeight;
        }
    }

    protected void drawBackground (Batch batch, float parentAlpha) {
        if (style.background != null) {
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
            style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
    }

    protected abstract void drawItem(Batch batch, BitmapFont font, int index, T item,
                                     float x, float y, float width, float height);

    /*
    protected void drawItem (Batch batch, BitmapFont font, List2DItem<T> item, float x, float y, float width, float height) {
        TextureRegion texture = textureFactory.getAsset(item.data.getIconID());
        String text = String.valueOf(item.data.getLevel());
        batch.draw(texture, x, y);
        font.draw(batch, text, x + width / 2, y + height / 2, 0, text.length(), width, alignment, false, "...");
    }
    */

    public void setCols (int cols) {
        if (cols <= 0) throw new IllegalArgumentException("cols cannot be <= 0.");
        this.cols = cols;
        invalidate();
    }

    public void setStyle (List.ListStyle style) {
        if (style == null) throw new IllegalArgumentException("style cannot be null.");
        this.style = style;
        invalidateHierarchy();
    }

    public List.ListStyle getStyle() {
        return style;
    }

    public Selection<List2DItem<T>> getSelection() {
        return selection;
    }

    public List2DItem<T> getSelected() { return selection.first(); }

    public void setSelected (List2DItem<T> item) {
        if (items.contains(item, false))
            selection.set(item);
        else if (selection.getRequired() && items.size > 0)
            selection.set(items.first());
        else
            selection.clear();
    }

    public int getSelectedIndex () {
        ObjectSet<List2DItem<T>> selected = selection.items();
        return selected.size == 0 ? -1 : items.indexOf(selected.first(), false);
    }

    public void setSelectedIndex (int index) {
        if (index < -1 || index >= items.size)
            throw new IllegalArgumentException("index must be >= -1 and < " + items.size + ": " + index);
        if (index == -1) {
            selection.clear();
        } else {
            selection.set(items.get(index));
        }
    }

    public List2DItem<T> getOverItem () {
        return overIndex == -1 ? null : items.get(overIndex);
    }

    public List2DItem<T> getPressedItem () {
        return pressedIndex == -1 ? null : items.get(pressedIndex);
    }

    public List2DItem<T> getItemAt (float x, float y) {
        int index = getItemIndexAt(x, y);
        if (index == -1) return null;
        return items.get(index);
    }

    public int getItemIndexAt (float x, float y) {
        float height = getHeight();
        Drawable background = List2D.this.style.background;
        if (background != null) {
            height -= background.getTopHeight() + background.getBottomHeight();
            y -= background.getBottomHeight();
            x -= background.getLeftWidth();
        }
        int indexY = (int)((height - y) / itemHeight);
        int indexX = (int)((x) / itemWidth);
        if (indexY < 0 || indexX < 0) return -1;
        int index = indexY * cols + indexX;
        if (index >= items.size) return -1;
        return index;
    }

    public void setItems (T... newItems) {
        if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
        float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();

        items.clear();
        int id = 0;
        for (T newItem : newItems) items.add(new List2DItem<>(id++, newItem));
        overIndex = -1;
        pressedIndex = -1;

        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight()) invalidateHierarchy();
    }

    public void setItems (Iterable<T> newItems) {
        if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
        float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();

        items.clear();
        int id = 0;
        for (T item : newItems) items.add(new List2DItem<>(id++, item));
        overIndex = -1;
        pressedIndex = -1;

        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight()) invalidateHierarchy();
    }

    public void clearItems () {
        if (items.size == 0) return;
        items.clear();
        overIndex = -1;
        pressedIndex = -1;
        selection.clear();
        invalidateHierarchy();
    }

    public Array<T> getItems() {
        Array<T> itemsArr = new Array<>(items.size);
        for (List2DItem<T> item : items) itemsArr.add(item.data);
        return itemsArr;
    }

    public float getItemHeight () { return itemHeight; }

    public float getItemWidth () { return itemWidth; }

    public float getPrefWidth () {
        validate();
        return prefWidth;
    }

    public float getPrefHeight () {
        validate();
        return prefHeight;
    }

    public void setAlignment (int alignment) {
        this.alignment = alignment;
    }

    public void setTypeToSelect (boolean typeToSelect) {
        this.typeToSelect = typeToSelect;
    }

    public InputListener getKeyListener () {
        return keyListener;
    }

    @Override
    public void setCullingArea(Rectangle cullingArea) {
        this.cullingArea = cullingArea;
    }

    public Rectangle getCullingArea() { return cullingArea; }

    private static class List2DItem<T> {
        int index;
        T data;

        List2DItem(int index, T data) {
            this.index = index;
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof List2DItem)) return false;

            return ((List2DItem) o).index == this.index;
        }

        @Override
        public int hashCode() {
            return index;
        }

    }

}
