package com.mana_wars.model.db.core_entity_converter;

import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.db.entity.DBSkill;
import com.mana_wars.model.db.entity.UserSkill;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SkillConverterTest {

    @Test
    public void testToActiveSkill(){
        UserSkill insideUserSkill = mock(UserSkill.class);
        DBSkill skill = mock(DBSkill.class);
        when(skill.getId()).thenReturn(2);
        when(insideUserSkill.getLvl()).thenReturn(30);
        when(skill.getRarity()).thenReturn(1);
        when(skill.getManaCost()).thenReturn(100);
        when(skill.getCastTime()).thenReturn(10.0);
        when(skill.getCooldown()).thenReturn(5.0);
        when(skill.getName()).thenReturn("aaa");

        CompleteUserSkill userSkill = new CompleteUserSkill();
        userSkill.skill = skill;
        userSkill.characteristics = new ArrayList<>();
        userSkill.userSkill = insideUserSkill;

        ActiveSkill result = SkillConverter.toActiveSkill(userSkill);

        assertEquals(30, result.getLevel());
        assertEquals(Rarity.getRarityByID(1), result.getRarity());
        assertEquals(100, result.getManaCost());
        assertEquals(10.0, result.getCastTime(), 0.0001);
        assertEquals(5.0, result.getCooldown(), 0.0001);
        assertEquals("aaa", result.getName());;

    }


        @Test
    public void testToPassiveSkill(){
        UserSkill insideUserSkill = mock(UserSkill.class);
        DBSkill skill = mock(DBSkill.class);
        when(skill.getId()).thenReturn(2);
        when(insideUserSkill.getLvl()).thenReturn(30);
        when(skill.getRarity()).thenReturn(1);
        when(skill.getManaCost()).thenReturn(100);
        when(skill.getName()).thenReturn("aaa");

        CompleteUserSkill userSkill = new CompleteUserSkill();
        userSkill.skill = skill;
        userSkill.characteristics = new ArrayList<>();
        userSkill.userSkill = insideUserSkill;

        PassiveSkill result = SkillConverter.toPassiveSkill(userSkill);

        assertEquals(30, result.getLevel());
        assertEquals(Rarity.getRarityByID(1), result.getRarity());
        assertEquals(100, result.getManaCost());
        assertEquals("aaa", result.getName());;

    }

}