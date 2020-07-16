package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.skills_operations.SkillTable;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class OperationSkillsList2DTest {

    private Skill skill1, skill3;

    @Before
    public void setup() {
        skill1 = new Skill(1, 1, Rarity.EPIC, "1", new ArrayList<>());
        skill3 = new Skill(3, 1, Rarity.COMMON, "3", new ArrayList<>());
    }

    @Test
    public void insert_Rarity_ordered() {
        OperationSkillsList2D list = new OperationSkillsList2D(getStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5, true, SkillTable.ACTIVE_SKILLS, null);

        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", new ArrayList<>());
        list.setItems(Arrays.asList(skill1, skill2, skill3));

        Skill skill4 = new Skill(4, 1, Rarity.ARCANE, "4", new ArrayList<>());
        list.insert(0, skill4);

        assertEquals(skill4, list.getItem(2));
    }

    @Test
    public void insert_Name_ordered() {
        OperationSkillsList2D list = new OperationSkillsList2D(getStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5, true, SkillTable.ACTIVE_SKILLS, null);

        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", new ArrayList<>());
        list.setItems(Arrays.asList(skill1, skill2, skill3));

        Skill skill4 = new Skill(4, 1, Rarity.ARCANE, "4", new ArrayList<>());
        list.insert(1, skill4);

        assertEquals(skill4, list.getItem(2));
    }

    @Test
    public void insert_Level_ordered() {
        OperationSkillsList2D list = new OperationSkillsList2D(getStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5, true, SkillTable.ACTIVE_SKILLS, null);

        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", new ArrayList<>());
        list.setItems(Arrays.asList(skill1, skill2, skill3));

        Skill skill4 = new Skill(4, 4, Rarity.ARCANE, "2", new ArrayList<>());
        list.insert(2, skill4);

        assertEquals(skill4, list.getItem(1));
    }

    @Test
    public void removeIndex_ordered() {
        OperationSkillsList2D list = new OperationSkillsList2D(getStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5, true, SkillTable.ACTIVE_SKILLS, null);

        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", new ArrayList<>());
        list.setItems(Arrays.asList(skill1, skill2, skill3));

        assertEquals(skill2, list.removeIndex(1));

        assertEquals(skill1, list.getItem(0));
        assertEquals(skill3, list.getItem(1));
    }

    @Test
    public void insert_AtEmpty_unordered() {
        OperationSkillsList2D list = new OperationSkillsList2D(getStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5, false, SkillTable.ACTIVE_SKILLS, null);

        Skill skill2 = Skill.getEmpty();
        list.setItems(Arrays.asList(skill1, skill2, skill3));

        Skill skill4 = new Skill(4, 1, Rarity.ARCANE, "4", new ArrayList<>());
        list.insert(1, skill4);

        assertEquals(skill1, list.getItem(0));
        assertEquals(skill4, list.getItem(1));
        assertEquals(skill3, list.getItem(2));
    }

    @Test
    public void insert_AtActual_unordered() {
        OperationSkillsList2D list = new OperationSkillsList2D(getStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5, false, SkillTable.ALL_SKILLS, null);

        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", new ArrayList<>());
        list.setItems(Arrays.asList(skill1, skill2, skill3));

        Skill skill4 = new Skill(4, 1, Rarity.ARCANE, "4", new ArrayList<>());
        list.insert(1, skill4);

        assertEquals(skill1, list.getItem(0));
        assertEquals(skill4, list.getItem(1));
        assertEquals(skill2, list.getItem(2));
        assertEquals(skill3, list.getItem(3));
    }

    @Test
    public void removeIndex_unordered() {
        OperationSkillsList2D list = new OperationSkillsList2D(getStyle(),
                (batch, font, index, item, x, y, width, height) -> {}, 5, false, SkillTable.ALL_SKILLS, null);

        Skill skill2 = new Skill(2, 1, Rarity.ARCANE, "2", new ArrayList<>());
        list.setItems(Arrays.asList(skill1, skill2, skill3));

        assertEquals(skill2, list.removeIndex(1));

        assertEquals(skill1, list.getItem(0));
        assertEquals(Skill.getEmpty(), list.getItem(1));
        assertEquals(skill3, list.getItem(2));
    }

    private List.ListStyle getStyle() {
        return new List.ListStyle();
    }

}