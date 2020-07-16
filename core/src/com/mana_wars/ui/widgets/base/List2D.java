package com.mana_wars.ui.widgets.base;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

import java.util.ArrayList;

/**
 * Texture-oriented 2-dimensional list based on the original List implementation in Libgdx.
 *
 * @param <T>
 */
public class List2D<T> extends Widget implements Cullable {

    private List.ListStyle style;
    private final Array<List2DItem<T>> items = new Array<>();
    private final Selection<List2DItem<T>> selection = new Selection<>();
    private Rectangle cullingArea;
    private Float minWidth, minHeight;
    private float prefWidth, prefHeight;
    private float itemHeight;
    private float itemWidth;
    private int pressedIndex = -1, overIndex = -1;
    private InputListener keyListener;

    private final ListItemDrawer<? super T> listItemDrawer;

    private int cols;

    private int availItemID;

    public List2D(Skin skin, ListItemDrawer<? super T> listItemDrawer, int cols) {
        this(skin.get(List.ListStyle.class), listItemDrawer, cols);
    }

    public List2D(List.ListStyle style, ListItemDrawer<? super T> listItemDrawer, int cols) {
        this.listItemDrawer = listItemDrawer;

        selection.setActor(this);
        selection.setRequired(false);
        selection.setMultiple(true);
        //selection.setDisabled(true);

        setCols(cols);
        setStyle(style);
        setSize(getPrefWidth(), getPrefHeight());

        addKeyboardListeners();
        addTouchListeners();
    }

    protected void enableSelection() {
        selection.setDisabled(false);
    }

    private void addKeyboardListeners() {
        addListener(keyListener = new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
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
                        index = items.indexOf(getSelectedItem(), false) + cols;
                        if (index >= items.size) index -= items.size;
                        setSelectedIndex(index);
                        return true;
                    case Input.Keys.UP:
                        index = items.indexOf(getSelectedItem(), false) - cols;
                        if (index < 0) index += items.size;
                        setSelectedIndex(index);
                        return true;
                    case Input.Keys.LEFT:
                        index = items.indexOf(getSelectedItem(), false) - 1;
                        if (index < 0) index = items.size - 1;
                        setSelectedIndex(index);
                        return true;
                    case Input.Keys.RIGHT:
                        index = items.indexOf(getSelectedItem(), false) + 1;
                        if (index >= items.size) index = 0;
                        setSelectedIndex(index);
                        return true;
                    case Input.Keys.ESCAPE:
                        if (getStage() != null) getStage().setKeyboardFocus(null);
                        return true;
                }
                return false;
            }

            public boolean keyTyped(InputEvent event, char character) {
                return false;
            }
        });
    }

    protected void addTouchListeners() {
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return true;
                if (selection.isDisabled()) return true;
                if (getStage() != null) getStage().setKeyboardFocus(List2D.this);
                if (items.size == 0) return true;
                int index = getItemIndexAt(x, y);
                if (index == -1) return true;
                /*if (selection.contains(items.get(index)))
                    selection.remove(items.get(index));
                else
                    selection.choose(items.get(index));*/
                pressedIndex = index;
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return;
                pressedIndex = -1;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                overIndex = getItemIndexAt(x, y);
            }

            public boolean mouseMoved(InputEvent event, float x, float y) {
                overIndex = getItemIndexAt(x, y);
                return false;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == 0) pressedIndex = -1;
                if (pointer == -1) overIndex = -1;
            }
        });
    }

    public void layout() {
        // style.selection is null when Unit Testing
        if (style.selection == null) return;

        Drawable background = style.background;

        float containerWidth = getWidth();
        if (background != null) {
            containerWidth -= background.getLeftWidth() + background.getRightWidth();
        }
        itemHeight = itemWidth = containerWidth / cols;

        prefWidth = getWidth();
        prefHeight = (float) (Math.ceil((float) items.size / cols) * itemHeight);

        if (background != null) {
            prefHeight += background.getTopHeight() + background.getBottomHeight();
        }
        setHeight(Math.max(getHeight(), prefHeight));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();

        drawBackground(batch, parentAlpha);

        BitmapFont font = style.font;
        Drawable selectedDrawable = style.selection;
        Color fontColorSelected = style.fontColorSelected;
        Color fontColorUnselected = style.fontColorUnselected;

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        float x = getX(), y = getY(), height = getHeight();
        float itemX = 0;
        float itemY = height;

        Drawable background = style.background;
        if (background != null) {
            float leftWidth = background.getLeftWidth();
            x += leftWidth;
            itemY -= background.getTopHeight();
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
                    if (drawable != null)
                        drawable.draw(batch, x + itemX, y + itemY - itemHeight, itemWidth, itemHeight);
                    drawItem(batch, font, item.id, item.data, x + itemX, y + itemY - itemHeight, itemWidth, itemHeight);
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

    private void drawBackground(Batch batch, float parentAlpha) {
        if (style.background != null) {
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
            style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
    }

    private void drawItem(Batch batch, BitmapFont font, int index, T item,
                          float x, float y, float width, float height) {
        listItemDrawer.draw(batch, font, index, item, x, y, width, height);
    }

    private void setCols(int cols) {
        if (cols <= 0) throw new IllegalArgumentException("cols cannot be <= 0.");
        this.cols = cols;
        invalidate();
    }

    private void setStyle(List.ListStyle style) {
        if (style == null) throw new IllegalArgumentException("style cannot be null.");
        this.style = style;
        invalidateHierarchy();
    }

    public Selection<List2DItem<T>> getSelection() {
        return selection;
    }

    public T getSelected() {
        return getSelectedItem().data;
    }

    private List2DItem<T> getSelectedItem() {
        return selection.first();
    }

    public int add(T item) {
        if (item == null) throw new IllegalArgumentException("List2D.add: item cannot be null");
        if (availItemID == Integer.MAX_VALUE)
            refactorItems();

        items.add(new List2DItem<>(availItemID++, item));
        return items.size - 1;
    }

    private void refactorItems() {
        setItems(getItemsCopy());
    }

    public void setItem(int index, T item) {
        items.get(index).data = item;
    }

    public int insert(int index, T item) {
        if (item == null) throw new IllegalArgumentException("List2D.insert: item cannot be null");
        if (index < 0) throw new IllegalArgumentException("List2D.insert: index cannot be < 0");
        if (index > items.size)
            throw new IllegalArgumentException("List2D.insert: item cannot be > itemsSize");
        if (availItemID == Integer.MAX_VALUE)
            refactorItems();

        items.insert(index, new List2DItem<>(availItemID++, item));
        return index;
    }

    private void setSelectedItem(List2DItem<T> item) {
        if (items.contains(item, false))
            selection.set(item);
        else if (selection.getRequired() && items.size > 0)
            selection.set(items.first());
        else
            selection.clear();
    }

    public int getSelectedIndex() {
        ObjectSet<List2DItem<T>> selected = selection.items();
        return selected.size == 0 ? -1 : items.indexOf(selected.first(), false);
    }

    public void setSelectedIndex(int index) {
        if (selection.isDisabled()) return;
        if (index < -1 || index >= items.size)
            throw new IllegalArgumentException("index must be >= -1 and < " + items.size + ": " + index);
        if (index == -1) {
            selection.clear();
        } else {
            selection.set(items.get(index));
        }
    }

    public void setSelectedIndices(Iterable<? extends Integer> indices) {
        for (Integer index : indices) selection.add(items.get(index));
    }

    public T removeIndex(int index) {
        if (index < 0 || index >= items.size)
            throw new IllegalArgumentException("index must be >= 0 and < " + items.size + ": " + index);
        return items.removeIndex(index).data;
    }

    public T getItemAt(float x, float y) {
        List2DItem<T> item = getListItemAt(x, y);
        return item == null ? null : item.data;
    }

    private List2DItem<T> getListItemAt(float x, float y) {
        try {
            int index = getItemIndexAt(x, y);
            return items.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getItemIndexAt(float x, float y) {
        float height = getHeight();
        Drawable background = List2D.this.style.background;
        if (background != null) {
            height -= background.getTopHeight() + background.getBottomHeight();
            y -= background.getBottomHeight();
            x -= background.getLeftWidth();
        }
        if (x < 0 || y < 0 || y > height) return -1;
        int indexY = (int) ((height - y) / itemHeight);
        int indexX = (int) ((x) / itemWidth);
        if (indexY < 0 || indexX < 0) return -1;
        int index = indexY * cols + indexX;
        if (index >= items.size) return -1;
        return index;
    }

    @SafeVarargs
    public final void setItems(T... newItems) {
        if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
        float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();

        clearItems();
        availItemID = 0;
        for (T newItem : newItems) items.add(new List2DItem<>(availItemID++, newItem));

        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight())
            invalidateHierarchy();
    }

    public void setItems(Iterable<? extends T> newItems) {
        if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
        float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();

        clearItems();
        availItemID = 0;
        for (T item : newItems) items.add(new List2DItem<>(availItemID++, item));

        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight())
            invalidateHierarchy();
    }

    private void clearItems() {
        if (items.size == 0) return;
        items.clear();
        overIndex = -1;
        pressedIndex = -1;
        selection.clear();
    }

    public int size() {
        return items.size;
    }

    public T getItem(int index) {
        List2DItem<T> item = items.get(index);
        return item == null ? null : item.data;
    }

    public Iterable<T> getItemsCopy() {
        java.util.List<T> itemsArr = new ArrayList<>();
        for (List2DItem<T> item : items) itemsArr.add(item.data);
        return itemsArr;
    }

    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public float getPrefWidth() {
        validate();
        if (minWidth != null)
            prefWidth = Math.max(prefWidth, minWidth);
        return prefWidth;
    }

    public float getPrefHeight() {
        validate();
        if (minHeight != null)
            prefHeight = Math.max(prefHeight, minHeight);
        return prefHeight;
    }

    protected void setPressedIndex(int pressedIndex) {
        this.pressedIndex = pressedIndex;
    }

    protected void setOverIndex(int overIndex) {
        this.overIndex = overIndex;
    }

    @Override
    public void setCullingArea(Rectangle cullingArea) {
        this.cullingArea = cullingArea;
    }

    public Rectangle getCullingArea() {
        return cullingArea;
    }

    private static class List2DItem<T> {
        private final int id;
        private T data;

        private List2DItem(int id, T data) {
            this.id = id;
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof List2DItem)) return false;

            return ((List2DItem) o).id == this.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
