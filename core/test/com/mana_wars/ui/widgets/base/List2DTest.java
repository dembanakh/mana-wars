package com.mana_wars.ui.widgets.base;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.GdxTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class List2DTest {

    private List2D<Integer> list;
    private Integer item1, item2;

    @Before
    public void setup() {
        Skin skin = new Skin();
        skin.add("default", new List.ListStyle());
        list = new List2D<>(skin,
                (batch, font, index, item, x, y, width, height) -> {
                }, 5);
        item1 = 1;
        item2 = 2;
        list.setItems(item1, item2);
    }

    @Test
    public void setSelectedIndex_minus1() {
        list.setSelectedIndex(0);
        list.setSelectedIndex(-1);
        assertEquals(0, list.getSelection().size());
    }

    @Test
    public void testSetItems() {
        assertEquals(item1, list.getItem(0));
        assertEquals(item2, list.getItem(1));
    }

}