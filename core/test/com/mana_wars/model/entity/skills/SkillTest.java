package com.mana_wars.model.entity.skills;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.base.Characteristic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SkillTest {

    private Skill skill;
    @Mock
    private SkillCharacteristic sc1;
    @Mock
    private SkillCharacteristic sc2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
        when(enemy.isAlive()).thenReturn(true);
        skill.activate(self, enemy);

        verify(self).applySkillCharacteristic(sc1, 1);
        verify(enemy).applySkillCharacteristic(sc2, 1);
    }

    @Test
    public void testGetManaCost() {
        when(sc1.isManaCost()).thenReturn(true);
        when(sc1.getValue(1)).thenReturn(10);
        when(sc2.getCharacteristic()).thenReturn(Characteristic.HEALTH);

        assertEquals(10, skill.getManaCost());
    }

    @Test
    public void testGetCharacteristics() {
        when(sc1.isManaCost()).thenReturn(true);
        when(sc2.isManaCost()).thenReturn(false);

        int size = 0;
        SkillCharacteristic characteristic = null;
        for (SkillCharacteristic sc : skill.getCharacteristics()) {
            size++;
            characteristic = sc;
        }

        assertEquals(1, size);
        assertEquals(sc2, characteristic);
    }

}