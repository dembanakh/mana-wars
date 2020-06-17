package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattleParticipantTest {

    private BattleParticipant bp;

    @Before
    public void setup(){
        bp = new BattleParticipant("a", 100, Collections.emptyList(), Collections.emptyList()) {

            @Override
            public void update(double currentTime) {

            }

        };
    }

    @Test
    public void testGetCharacteristicValue() {
        assertEquals(100, bp.getCharacteristicValue(Characteristic.HEALTH));
    }

    @Test
    public void testApplySkillCharacteristic() {

        SkillCharacteristic sc = mock(SkillCharacteristic.class);

        when(sc.getCharacteristic()).thenReturn(Characteristic.HEALTH);
        when(sc.getValue()).thenReturn(10);
        when(sc.getChangeType()).thenReturn(ValueChangeType.DECREASE);
        bp.applySkillCharacteristic(sc);

        assertEquals(90, bp.getCharacteristicValue(Characteristic.HEALTH));

        verify(sc).getCharacteristic();
        verify(sc).getValue();
        verify(sc).getChangeType();
        verifyNoMoreInteractions(sc);
    }
}