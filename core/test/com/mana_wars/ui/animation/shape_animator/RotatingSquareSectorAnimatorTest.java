package com.mana_wars.ui.animation.shape_animator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

import org.junit.Before;
import org.junit.Test;

import space.earlygrey.shapedrawer.ShapeDrawer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RotatingSquareSectorAnimatorTest {

    private RotatingSquareSectorAnimator animator;

    private ShapeDrawer drawer;
    private float[] vertices;

    @Before
    public void setup() {
        animator = new RotatingSquareSectorAnimator(new Color(0, 0, 0, 0));
        drawer = new ShapeDrawer(null, new TextureRegion()) {
            @Override
            public void filledPolygon(Polygon polygon) {
                vertices = polygon.getVertices();
            }
        };
    }

    @Test
    public void testAnimate_0outOf4() {
        animator.animate(drawer, 0, 0, 100, 100, 4, 4);

        int points = vertices.length >> 1;
        float[] x = new float[points];
        float[] y = new float[points];
        extract(x, y);
        int b = findCenter(x, y);
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(50, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(100, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(100, x[b]));
        assertEquals(0, Float.compare(100, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(100, x[b]));
        assertEquals(0, Float.compare(0, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(0, x[b]));
        assertEquals(0, Float.compare(0, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(0, x[b]));
        assertEquals(0, Float.compare(100, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(100, y[b]));
    }

    @Test
    public void testAnimate_1outOf4() {
        animator.animate(drawer, 0, 0, 100, 100, 3, 4);

        int points = vertices.length >> 1;
        float[] x = new float[points];
        float[] y = new float[points];
        extract(x, y);
        int b = findCenter(x, y);
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(50, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(100, x[b]));
        assertEquals(0, Float.compare(50, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(100, x[b]));
        assertEquals(0, Float.compare(0, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(0, x[b]));
        assertEquals(0, Float.compare(0, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(0, x[b]));
        assertEquals(0, Float.compare(100, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(100, y[b]));
    }

    @Test
    public void testAnimate_2outOf4() {
        animator.animate(drawer, 0, 0, 100, 100, 2, 4);

        int points = vertices.length >> 1;
        float[] x = new float[points];
        float[] y = new float[points];
        extract(x, y);
        int b = findCenter(x, y);
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(50, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(0, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(0, x[b]));
        assertEquals(0, Float.compare(0, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(0, x[b]));
        assertEquals(0, Float.compare(100, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(100, y[b]));
    }

    @Test
    public void testAnimate_3outOf4() {
        animator.animate(drawer, 0, 0, 100, 100, 1, 4);

        int points = vertices.length >> 1;
        float[] x = new float[points];
        float[] y = new float[points];
        extract(x, y);
        int b = findCenter(x, y);
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(50, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(0, x[b]));
        assertEquals(0, Float.compare(50, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(0, x[b]));
        assertEquals(0, Float.compare(100, y[b]));

        b = (b + 1) % x.length;
        assertEquals(0, Float.compare(50, x[b]));
        assertEquals(0, Float.compare(100, y[b]));
    }

    private void extract(float[] x, float[] y) {
        for (int i = 0, j = 1, k = 0; k < x.length; i += 2, j += 2, k++) {
            x[k] = vertices[i];
            y[k] = vertices[j];
        }
    }

    private int findCenter(float[] x, float[] y) {
        for (int i = 0; i < x.length; i++) {
            if (Float.compare(x[i], 50) == 0 && Float.compare(y[i], 50) == 0) return i;
        }
        fail("No (50, 50) in drawn polygon");
        return 0;
    }
}