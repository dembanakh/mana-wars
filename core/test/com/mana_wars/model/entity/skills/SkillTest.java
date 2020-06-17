package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class SkillTest {

    private Skill skill;
    private SkillCharacteristic sc1;
    private SkillCharacteristic sc2;

    @Before
    public void setup() {
        sc1 = mock(SkillCharacteristic.class);
        sc2 = mock(SkillCharacteristic.class);
        List<SkillCharacteristic> scList = new ArrayList<>();
        scList.add(sc1);
        scList.add(sc2);
        skill = new Skill(1, 1, Rarity.COMMON, "name", scList);
    }

    @Test
    public void testActivate() {
        when(sc1.getTarget()).thenReturn(SkillCharacteristic.Target.SELF);
        when(sc2.getTarget()).thenReturn(SkillCharacteristic.Target.ENEMY);
        BattleParticipant self = mock(BattleParticipant.class);
        BattleParticipant enemy = mock(BattleParticipant.class);
        skill.activate(self, enemy);

        verify(self).applySkillCharacteristic(sc1);
        verify(enemy).applySkillCharacteristic(sc2);
        verifyNoMoreInteractions(self, enemy);
    }

    @Test
    public void testGetManaCost() {
        when(sc1.getCharacteristic()).thenReturn(Characteristic.MANA);
        when(sc1.getValue()).thenReturn(10);
        when(sc2.getCharacteristic()).thenReturn(Characteristic.HEALTH);

        assertEquals(10, skill.getManaCost());
    }

    @Test
    public void testGetDescription() {
        when(sc1.getCharacteristic()).thenReturn(Characteristic.MANA);
        when(sc1.getDescription()).thenReturn("a");
        when(sc2.getCharacteristic()).thenReturn(Characteristic.HEALTH);
        when(sc2.getDescription()).thenReturn("b");

        assertEquals("b\n", skill.getDescription());
    }

}