package com.mana_wars.ui.transform.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.GdxTestRunner;
import com.mana_wars.ui.layout_constraints.AbsoluteSizeConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteXPositionConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteYPositionConstraint;
import com.mana_wars.ui.layout_constraints.RelativeWidthConstraint;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class TransformBuilderTest {

    @BeforeClass
    public static void setup() {
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(1000);
        when(Gdx.graphics.getHeight()).thenReturn(1000);
    }

    @Test
    public void testDefaultBuild() {
        Transform transform = new TransformBuilder().build();
        assertEquals(0, Float.compare(0, transform.getX()));
        assertEquals(0, Float.compare(0, transform.getY()));
        assertEquals(0, Float.compare(100, transform.getWidth()));
        assertEquals(0, Float.compare(100, transform.getHeight()));
        assertEquals(Align.bottomLeft, transform.getAlign());
    }

    @Test
    public void test_AbsolutePos_AbsoluteSize_Build() {
        Transform transform = new TransformBuilder()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.right, 100))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 100))
                .setWidthConstraint(new AbsoluteSizeConstraint(200))
                .setHeightConstraint(new AbsoluteSizeConstraint(200))
                .build();
        assertEquals(0, Float.compare(900, transform.getX()));
        assertEquals(0, Float.compare(900, transform.getY()));
        assertEquals(0, Float.compare(200, transform.getWidth()));
        assertEquals(0, Float.compare(200, transform.getHeight()));
        assertEquals(Align.topRight, transform.getAlign());
    }

    @Test
    public void test_RelativeSize_Build() {
        Transform transform = new TransformBuilder()
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new RelativeWidthConstraint(25))
                .build();
        assertEquals(0, Float.compare(500, transform.getWidth()));
        assertEquals(0, Float.compare(250, transform.getHeight()));
    }
}