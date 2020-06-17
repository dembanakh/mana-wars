package com.mana_wars.model.db.core_entity_converter;

import com.mana_wars.model.db.entity.DBSkillCharacteristic;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharacteristicsConverterTest {
    @Test
    public void testToSkillCharacteristics() {
        List<DBSkillCharacteristic> input = new ArrayList<>();
        List<SkillCharacteristic> result = CharacteristicsConverter.toSkillCharacteristics(input);
        assertEquals(0, result.size());


        DBSkillCharacteristic mockSkill = mock(DBSkillCharacteristic.class);
        when(mockSkill.getValue()).thenReturn(2);
        when(mockSkill.getType()).thenReturn(1);
        when(mockSkill.getChangeType()).thenReturn(true);
        when(mockSkill.getTarget()).thenReturn(1);
        input.add(mockSkill);

        result = CharacteristicsConverter.toSkillCharacteristics(input);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getValue(1));
        assertEquals(Characteristic.getCharacteristicById(1), result.get(0).getCharacteristic());
        assertEquals(ValueChangeType.INCREASE, result.get(0).getChangeType());
        assertEquals(SkillCharacteristic.Target.SELF, result.get(0).getTarget());
    }

}