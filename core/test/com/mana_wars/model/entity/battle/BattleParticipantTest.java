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
    public void testGetCharacteristicValue(){
        BattleParticipant bp = new BattleParticipant(100,200,300,400);
        assertEquals(100, bp.getCharacteristicValue(Characteristic.HEALTH));
        assertEquals(200, bp.getCharacteristicValue(Characteristic.MANA));
        assertEquals(300, bp.getCharacteristicValue(Characteristic.COOLDOWN));
        assertEquals(400, bp.getCharacteristicValue(Characteristic.CAST_TIME));
    }


    @Test
    public void testApplySkillCharacteristic() {
        BattleParticipant bp = new BattleParticipant(100,200,300,400);
        SkillCharacteristic sc = mock(SkillCharacteristic.class);

        when(sc.getCharacteristic()).thenReturn(Characteristic.HEALTH);
        when(sc.getValue()).thenReturn(10);
        when(sc.getChangeType()).thenReturn(ValueChangeType.DECREASE);
        bp.applySkillCharacteristic(sc);

        assertEquals(90, bp.getCharacteristicValue(Characteristic.HEALTH));
        assertEquals(200, bp.getCharacteristicValue(Characteristic.MANA));
        assertEquals(300, bp.getCharacteristicValue(Characteristic.COOLDOWN));
        assertEquals(400, bp.getCharacteristicValue(Characteristic.CAST_TIME));


        verify(sc).getCharacteristic();
        verify(sc).getValue();
        verify(sc).getChangeType();
        verifyNoMoreInteractions(sc);
    }
}