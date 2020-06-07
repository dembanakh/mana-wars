package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.mana_wars.ui.widgets.skills_list_2d.List2D;

import org.junit.Test;

import static org.junit.Assert.*;

public class List2DTest {

    @Test
    public void getSelected() {
        com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer> list = new com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer>(new List.ListStyle(), 5) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Integer item,
                                    float x, float y, float width, float height) {

            }
        };
        Integer item1 = 1;
        Integer item2 = 2;
        list.setItems(item1, item2);

        list.setSelectedIndex(0);
        assertEquals(item1, list.getSelected());
    }

    @Test
    public void setSelectedIndex_minus1() {
        com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer> list = new com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer>(new List.ListStyle(), 5) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Integer item, float x, float y, float width, float height) {

            }
        };
        Integer item1 = 1;
        Integer item2 = 2;
        list.setItems(item1, item2);

        list.setSelectedIndex(0);
        list.setSelectedIndex(-1);
        assertEquals(0, list.getSelection().size());
    }

    @Test
    public void setSelectedIndex_simple() {
        com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer> list = new com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer>(new List.ListStyle(), 5) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Integer item, float x, float y, float width, float height) {

            }
        };
        Integer item1 = 1;
        Integer item2 = 2;
        list.setItems(item1, item2);

        list.setSelectedIndex(0);
        assertEquals(0, list.getSelectedIndex());
    }

    @Test
    public void clearItems() {
        com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer> list = new com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer>(new List.ListStyle(), 5) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Integer item, float x, float y, float width, float height) {

            }
        };
        Integer item1 = 1;
        Integer item2 = 2;
        list.setItems(item1, item2);
        list.clearItems();

        assertEquals(0, list.getItemsSize());
    }

    @Test
    public void testSetItems() {
        com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer> list = new com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer>(new List.ListStyle(), 5) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Integer item, float x, float y, float width, float height) {

            }
        };
        Integer item1 = 1;
        Integer item2 = 2;
        list.setItems(item1, item2);

        assertEquals(item1, list.getItem(0));
        assertEquals(item2, list.getItem(1));
    }

    @Test
    public void testGetItems_empty() {
        com.mana_wars.ui.widgets.skills_list_2d.List2D<Integer> list = new List2D<Integer>(new List.ListStyle(), 5) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Integer item, float x, float y, float width, float height) {

            }
        };

        assertEquals(0, list.getItemsSize());
    }
}