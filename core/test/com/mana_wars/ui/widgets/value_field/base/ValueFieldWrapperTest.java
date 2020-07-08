package com.mana_wars.ui.widgets.value_field.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ValueFieldWrapperTest {

    private ValueFieldWrapper<String, Integer> wrapper;

    @Mock
    ValueField<String, Integer> valueField;

    @Before
    public void setup() {
        wrapper = new ValueFieldWrapper<>();
    }

    @Test
    public void testSetField() throws Exception {
        try {
            wrapper.setInitialData("A");
            wrapper.accept(1);
        } catch (Exception e) {
            fail();
        }
        wrapper.setField(valueField);
        verify(valueField, times(1)).setInitialData("A");
        verify(valueField, times(1)).accept(1);
        wrapper.accept(2);
        verify(valueField, times(1)).accept(2);
        verifyNoMoreInteractions(valueField);
    }

}