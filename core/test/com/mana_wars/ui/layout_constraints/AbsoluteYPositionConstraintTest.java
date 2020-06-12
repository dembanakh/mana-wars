package com.mana_wars.ui.layout_constraints;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.utils.Align;

import org.junit.BeforeClass;
import org.junit.Test;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbsoluteYPositionConstraintTest {

    private PositionConstraint constraint;

    @BeforeClass
    public static void setup() {
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(1000);
        when(Gdx.graphics.getHeight()).thenReturn(1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongAlign() {
        constraint = new AbsoluteYPositionConstraint(Align.left | Align.right, 100);
    }

    @Test
    public void testGetAlign() {
        constraint = new AbsoluteYPositionConstraint(Align.bottom, 100);
        assertEquals(Align.bottom, constraint.getAlign());
    }

    @Test
    public void testGetPosition_AlignBottom() {
        constraint = new AbsoluteYPositionConstraint(Align.bottom, 100);
        assertEquals(100, constraint.getPosition(), Double.MIN_VALUE);
    }

    @Test
    public void testGetPosition_AlignTop() {
        constraint = new AbsoluteYPositionConstraint(Align.top, 100);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getHeight()).thenReturn(1000);
        assertEquals(900, constraint.getPosition(), Double.MIN_VALUE);
    }

}