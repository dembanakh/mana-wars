package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.mana_wars.ui.widgets.base.List2D;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class List2DTest {

    private com.mana_wars.ui.widgets.base.List2D<Integer> list;
    private Integer item1, item2;

    @Before
    public void setup(){
        list = new com.mana_wars.ui.widgets.base.List2D<>(new List.ListStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5);
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

    @Test
    public void testGetItems_empty() {
        com.mana_wars.ui.widgets.base.List2D<Integer> list = new List2D<Integer>(new List.ListStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5);

        assertEquals(0, list.size());
    }
}