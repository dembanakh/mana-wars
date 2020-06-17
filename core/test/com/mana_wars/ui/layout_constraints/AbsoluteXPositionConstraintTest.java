package com.mana_wars.ui.layout_constraints;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.utils.Align;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbsoluteXPositionConstraintTest {

    private PositionConstraint constraint;

    @Test(expected = IllegalArgumentException.class)
    public void testWrongAlign() {
        constraint = new AbsoluteXPositionConstraint(Align.top | Align.bottom, 100);
    }

    @Test
    public void testGetAlign() {
        constraint = new AbsoluteXPositionConstraint(Align.left, 100);
        assertEquals(Align.left, constraint.getAlign());
    }

    @Test
    public void testGetPosition_AlignLeft() {
        constraint = new AbsoluteXPositionConstraint(Align.left, 100);
        assertEquals(100, constraint.getPosition(), Double.MIN_VALUE);
    }

    @Test
    public void testGetPosition_AlignRight() {
        constraint = new AbsoluteXPositionConstraint(Align.right, 100);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(1000);
        assertEquals(900, constraint.getPosition(), Double.MIN_VALUE);
    }

}