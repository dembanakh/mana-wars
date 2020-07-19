package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.CharacteristicsStorage;
import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class SkillCharacteristicApplicationModeTest {

    @Mock
    private CharacteristicsStorage storage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDefaultHEALTH() {
        when(storage.getValue(Characteristic.HEALTH)).thenReturn(100);
        SkillCharacteristicApplicationMode.DEFAULT.applySkillCharacteristic(
                new SkillCharacteristic(10, Characteristic.HEALTH, ValueChangeType.DECREASE,
                        -1, UpgradeFunction.LINEAR, 1),
                1, storage);
        verify(storage).setValue(Characteristic.HEALTH, 90);
    }

    @Test
    public void testDefaultMANA() {
        when(storage.getValue(Characteristic.MANA)).thenReturn(100);
        SkillCharacteristicApplicationMode.DEFAULT.applySkillCharacteristic(
                new SkillCharacteristic(10, Characteristic.MANA, ValueChangeType.DECREASE,
                        0, UpgradeFunction.LINEAR, 1),
                1, storage);
        verify(storage).setValue(Characteristic.MANA, 90);
    }

    @Test
    public void testNoManaConsumptionHEALTH() {
        when(storage.getValue(Characteristic.HEALTH)).thenReturn(100);
        SkillCharacteristicApplicationMode.NO_MANA_CONSUMPTION.applySkillCharacteristic(
                new SkillCharacteristic(10, Characteristic.HEALTH, ValueChangeType.DECREASE,
                        -1, UpgradeFunction.LINEAR, 1),
                1, storage);
        verify(storage).setValue(Characteristic.HEALTH, 90);
    }

    @Test
    public void testNoManaConsumptionMANA() {
        when(storage.getValue(Characteristic.MANA)).thenReturn(100);
        SkillCharacteristicApplicationMode.NO_MANA_CONSUMPTION.applySkillCharacteristic(
                new SkillCharacteristic(10, Characteristic.MANA, ValueChangeType.DECREASE,
                        0, UpgradeFunction.LINEAR, 1),
                1, storage);

        verifyNoMoreInteractions(storage);
    }

}