package com.mana_wars.model.db.core_entity_converter;

import com.mana_wars.model.db.entity.DBSkillCharacteristic;
import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacteristicsConverterTest {

    private List<DBSkillCharacteristic> input;
    private List<SkillCharacteristic> output;

    @Before
    public void setup() {
        input = new ArrayList<>();
    }

    @Test
    public void testToSkillCharacteristics_empty() {
        output = CharacteristicsConverter.toSkillCharacteristics(input);
        assertEquals(0, output.size());
    }

    @Test
    public void testToSkillCharacteristics() {
        DBSkillCharacteristic mockSkill = mock(DBSkillCharacteristic.class);
        when(mockSkill.getValue()).thenReturn(2);
        when(mockSkill.getType()).thenReturn(1);
        when(mockSkill.getChangeType()).thenReturn(true);
        when(mockSkill.getTarget()).thenReturn(1);
        when(mockSkill.getUpgradeFunction()).thenReturn("LINEAR");
        when(mockSkill.getLevelMultiplier()).thenReturn(1);
        input.add(mockSkill);

        output = CharacteristicsConverter.toSkillCharacteristics(input);
        assertEquals(1, output.size());
        assertEquals(2, output.get(0).getValue(1));
        assertEquals(Characteristic.getCharacteristicById(1), output.get(0).getCharacteristic());
        assertEquals(ValueChangeType.INCREASE, output.get(0).getChangeType());
        assertEquals(SkillCharacteristic.Target.SELF, output.get(0).getTarget());
        assertEquals(UpgradeFunction.LINEAR, output.get(0).getUpgradeFunction());
        assertEquals(0, Double.compare(1, output.get(0).getLevelMultiplier()));
    }

}