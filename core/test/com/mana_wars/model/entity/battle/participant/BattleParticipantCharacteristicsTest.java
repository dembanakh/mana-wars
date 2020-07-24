package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BattleParticipantCharacteristicsTest {

    private BattleParticipantCharacteristics characteristics;

    @Before
    public void setup() {
        characteristics = new BattleParticipantCharacteristics(100);
    }

    @Test
    public void testSetHealthValue() {
        characteristics.setValue(Characteristic.HEALTH, 120);
        assertEquals(100, characteristics.getValue(Characteristic.HEALTH));
    }

}