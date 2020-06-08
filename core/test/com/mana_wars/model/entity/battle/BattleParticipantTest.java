package com.mana_wars.model.entity.battle;


import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class BattleParticipantTest {

    @Test
    public void testGetCharacteristicValue() {
        BattleParticipant bp = new BattleParticipant("a", 100, 200) {
            @Override
            public void start() {

            }

            @Override
            public void act(float delta) {

            }
        };
        assertEquals(100, bp.getCharacteristicValue(Characteristic.HEALTH));
        assertEquals(200, bp.getCharacteristicValue(Characteristic.MANA));
    }


    @Test
    public void testApplySkillCharacteristic() {
        BattleParticipant bp = new BattleParticipant("a", 100,200) {

            @Override
            public void start() {

            }

            @Override
            public void act(float delta) {

            }
        };
        SkillCharacteristic sc = mock(SkillCharacteristic.class);

        when(sc.getCharacteristic()).thenReturn(Characteristic.HEALTH);
        when(sc.getValue()).thenReturn(10);
        when(sc.getChangeType()).thenReturn(ValueChangeType.DECREASE);
        bp.applySkillCharacteristic(sc);

        assertEquals(90, bp.getCharacteristicValue(Characteristic.HEALTH));
        assertEquals(200, bp.getCharacteristicValue(Characteristic.MANA));

        verify(sc).getCharacteristic();
        verify(sc).getValue();
        verify(sc).getChangeType();
        verifyNoMoreInteractions(sc);
    }
}