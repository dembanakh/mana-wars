package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BattleParticipantTest {

    private com.mana_wars.model.entity.battle.participant.BattleParticipant bp;

    @Before
    public void setup() {
        bp = new BattleParticipant("a", 100, Collections.emptyList(), Collections.emptyList(), null) {

            @Override
            public void update(double currentTime) {

            }

        };
    }

    @Test
    public void testGetCharacteristicValue() {
        assertEquals(100, bp.getCharacteristicValue(com.mana_wars.model.entity.base.Characteristic.HEALTH));
    }

    @Test
    public void testApplySkillCharacteristic() {

        SkillCharacteristic sc = mock(SkillCharacteristic.class);

        when(sc.getCharacteristic()).thenReturn(com.mana_wars.model.entity.base.Characteristic.HEALTH);
        when(sc.getValue(1)).thenReturn(10);
        when(sc.getChangeType()).thenReturn(ValueChangeType.DECREASE);
        bp.applySkillCharacteristic(sc, 1);

        assertEquals(90, bp.getCharacteristicValue(Characteristic.HEALTH));

        verify(sc).getCharacteristic();
        verify(sc).getValue(1);
        verify(sc).getChangeType();
    }
}