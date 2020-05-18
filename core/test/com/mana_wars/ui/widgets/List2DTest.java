package com.mana_wars.ui.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mana_wars.model.entity.base.GameItem;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class List2DTest {

    @Test
    public void getSelected() {
        List2D<Skill> list = new List2D<Skill>(new List.ListStyle(), 5, null) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item, float x, float y, float width, float height) {

            }
        };
        Skill skill1 = new Skill(1, 1, Rarity.COMMON, 1, "item1", new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.COMMON, 1, "item2", new ArrayList<>());
        list.setItems(skill1, skill2);

        list.setSelectedIndex(0);
        assertEquals(skill1, list.getSelected());
    }

    @Test
    public void setSelectedIndex_minus1() {
        List2D<Skill> list = new List2D<Skill>(new List.ListStyle(), 5, null) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item, float x, float y, float width, float height) {

            }
        };
        Skill skill1 = new Skill(1, 1, Rarity.COMMON, 1, "item1", new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.COMMON, 1, "item2", new ArrayList<>());
        list.setItems(skill1, skill2);

        list.setSelectedIndex(0);
        list.setSelectedIndex(-1);
        assertEquals(0, list.getSelection().size());
    }

    @Test
    public void setSelectedIndex_simple() {
        List2D<Skill> list = new List2D<Skill>(new List.ListStyle(), 5, null) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item, float x, float y, float width, float height) {

            }
        };
        Skill skill1 = new Skill(1, 1, Rarity.COMMON, 1, "item1", new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.COMMON, 1, "item2", new ArrayList<>());
        list.setItems(skill1, skill2);

        list.setSelectedIndex(0);
        assertEquals(0, list.getSelectedIndex());
    }

    @Test
    public void clearItems() {
        List2D<Skill> list = new List2D<Skill>(new List.ListStyle(), 5, null) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item, float x, float y, float width, float height) {

            }
        };
        Skill skill1 = new Skill(1, 1, Rarity.COMMON, 1, "item1", new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.COMMON, 1, "item2", new ArrayList<>());
        list.setItems(skill1, skill2);
        list.clearItems();

        assertEquals(0, list.getItems().size);
    }

    @Test
    public void testSetItems() {
        List2D<Skill> list = new List2D<Skill>(new List.ListStyle(), 5, null) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item, float x, float y, float width, float height) {

            }
        };
        Skill skill1 = new Skill(1, 1, Rarity.COMMON, 1, "item1", new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.COMMON, 1, "item2", new ArrayList<>());
        list.setItems(skill1, skill2);

        assertEquals(new Array<>(new Skill[] {skill1, skill2}), list.getItems());
    }

    @Test
    public void testGetItems_empty() {
        List2D<Skill> list = new List2D<Skill>(new List.ListStyle(), 5, null) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item, float x, float y, float width, float height) {

            }
        };

        assertEquals(0, list.getItems().size);
    }
}