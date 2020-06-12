package com.mana_wars.ui.layout_constraints;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RelativeWidthConstraintTest {

    @BeforeClass
    public static void setup() {
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(1000);
        when(Gdx.graphics.getHeight()).thenReturn(1000);
    }

    @Test
    public void testGetSize() {
        SizeConstraint constraint = new RelativeWidthConstraint(25);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(1000);
        assertEquals(250, constraint.getSize(), Double.MIN_VALUE);
    }

}