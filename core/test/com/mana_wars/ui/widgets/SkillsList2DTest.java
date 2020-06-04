package com.mana_wars.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SkillsList2DTest {

    @Test
    public void insert_Rarity_ordered() {
        SkillsList2D list = new SkillsList2D(getStyle(), 5, null, null, true);

        Skill skill1 = new Skill(1, 1, Rarity.EPIC, "1", 10, new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", 10, new ArrayList<>());
        Skill skill3 = new Skill(3, 1, Rarity.COMMON, "3", 10, new ArrayList<>());
        list.setItems(skill1, skill2, skill3);

        Skill skill4 = new Skill(4, 1, Rarity.ARCANE, "4", 10, new ArrayList<>());
        list.insert(0, skill4);

        assertEquals(skill4, list.getItem(2));
    }

    @Test
    public void insert_Name_ordered() {
        SkillsList2D list = new SkillsList2D(getStyle(), 5, null, null, true);

        Skill skill1 = new Skill(1, 1, Rarity.EPIC, "1", 10, new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", 10, new ArrayList<>());
        Skill skill3 = new Skill(3, 1, Rarity.COMMON, "3", 10, new ArrayList<>());
        list.setItems(skill1, skill2, skill3);

        Skill skill4 = new Skill(4, 1, Rarity.ARCANE, "4", 10, new ArrayList<>());
        list.insert(1, skill4);

        assertEquals(skill4, list.getItem(2));
    }

    @Test
    public void insert_Level_ordered() {
        SkillsList2D list = new SkillsList2D(getStyle(), 5, null, null, true);

        Skill skill1 = new Skill(1, 1, Rarity.EPIC, "1", 10, new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", 10, new ArrayList<>());
        Skill skill3 = new Skill(3, 1, Rarity.COMMON, "3", 10, new ArrayList<>());
        list.setItems(skill1, skill2, skill3);

        Skill skill4 = new Skill(4, 4, Rarity.ARCANE, "2", 10, new ArrayList<>());
        list.insert(2, skill4);

        assertEquals(skill4, list.getItem(1));
    }

    @Test
    public void removeIndex_ordered() {
        SkillsList2D list = new SkillsList2D(getStyle(), 5, null, null, true);

        Skill skill1 = new Skill(1, 1, Rarity.EPIC, "1", 10, new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", 10, new ArrayList<>());
        Skill skill3 = new Skill(3, 1, Rarity.COMMON, "3", 10, new ArrayList<>());
        list.setItems(skill1, skill2, skill3);

        assertEquals(skill2, list.removeIndex(1));

        assertEquals(Arrays.asList(skill1, skill3), list.getItemsCopy());
    }

    @Test
    public void insert_AtEmpty_unordered() {
        SkillsList2D list = new SkillsList2D(getStyle(), 5, null, null, false);

        Skill skill1 = new Skill(1, 1, Rarity.EPIC, "1", 10, new ArrayList<>());
        Skill skill2 = Skill.getEmpty();
        Skill skill3 = new Skill(3, 1, Rarity.COMMON, "3", 10, new ArrayList<>());
        list.setItems(skill1, skill2, skill3);

        Skill skill4 = new Skill(4, 1, Rarity.ARCANE, "4", 10, new ArrayList<>());
        list.insert(1, skill4);

        assertEquals(Arrays.asList(skill1, skill4, skill3), list.getItemsCopy());
    }

    @Test
    public void insert_AtActual_unordered() {
        SkillsList2D list = new SkillsList2D(getStyle(), 5, null, null, false);

        Skill skill1 = new Skill(1, 1, Rarity.EPIC, "1", 10, new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", 10, new ArrayList<>());
        Skill skill3 = new Skill(3, 1, Rarity.COMMON, "3", 10, new ArrayList<>());
        list.setItems(skill1, skill2, skill3);

        Skill skill4 = new Skill(4, 1, Rarity.ARCANE, "4", 10, new ArrayList<>());
        list.insert(1, skill4);

        assertEquals(Arrays.asList(skill1, skill4, skill2, skill3), list.getItemsCopy());
    }

    @Test
    public void removeIndex_unordered() {
        SkillsList2D list = new SkillsList2D(getStyle(), 5, null, null, false);

        Skill skill1 = new Skill(1, 1, Rarity.EPIC, "1", 10, new ArrayList<>());
        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", 10, new ArrayList<>());
        Skill skill3 = new Skill(3, 1, Rarity.COMMON, "3", 10, new ArrayList<>());
        list.setItems(skill1, skill2, skill3);

        assertEquals(skill2, list.removeIndex(1));

        assertEquals(Arrays.asList(skill1, Skill.getEmpty(), skill3), list.getItemsCopy());
    }

    private List.ListStyle getStyle() {
        List.ListStyle style = new List.ListStyle();
        return style;
    }

}