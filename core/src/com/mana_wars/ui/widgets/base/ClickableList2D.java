package com.mana_wars.ui.widgets.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.widgets.base.List2D;
import com.mana_wars.ui.widgets.base.ListItemConsumer;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

public class ClickableList2D<T> extends List2D<T> {

    private final ListItemConsumer<? super T> onItemClick;

    public ClickableList2D(Skin skin, ListItemDrawer<? super T> listItemDrawer,
                           int cols, ListItemConsumer<? super T> onItemClick) {
        this(skin.get(List.ListStyle.class), listItemDrawer, cols, onItemClick);
    }

    private ClickableList2D(List.ListStyle style, ListItemDrawer<? super T> listItemDrawer,
                            int cols, ListItemConsumer<? super T> onItemClick) {
        super(style, listItemDrawer, cols);
        this.onItemClick = onItemClick;
    }

    @Override
    protected void addTouchListeners() {
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return true;
                int index = getItemIndexAt(x, y);
                if (index == -1) return true;
                setPressedIndex(index);

                T item = getItem(index);
                if (item != null)
                    onItemClick.accept(item, index);

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return;
                setPressedIndex(-1);
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                setOverIndex(getItemIndexAt(x, y));
            }

            public boolean mouseMoved(InputEvent event, float x, float y) {
                setOverIndex(getItemIndexAt(x, y));
                return false;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == 0) setPressedIndex(-1);
                if (pointer == -1) setOverIndex(-1);
            }
        });
    }

}
