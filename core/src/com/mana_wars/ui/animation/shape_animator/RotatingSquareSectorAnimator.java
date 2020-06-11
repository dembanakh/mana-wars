package com.mana_wars.ui.animation.shape_animator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class RotatingSquareSectorAnimator extends ShapeAnimator {

    public RotatingSquareSectorAnimator(Color color) {
        super(color);
    }

    @Override
    public void animate(ShapeDrawer shapeDrawer, float x, float y, float width, float height,
                        double timeLeft, double duration) {
        shapeDrawer.setColor(getColor());
        shapeDrawer.filledPolygon(createSector(x, y, width, height, getAngle(timeLeft, duration)));
    }

    private static double getAngle(double timeLeft, double duration) {
        return 2 * Math.PI * (duration - timeLeft) / duration;
    }

    private static Polygon createSector(float x, float y, float width, float height, double angle) {
        float[] vertices;
        if (Double.compare(angle, Math.PI / 4) <= 0) {
            vertices = new float[7 * 2];
            addVertex(vertices, 0, x, y);
            addVertex(vertices, 1, x, y + height);
            addVertex(vertices, 2, x + width / 2, y + height);
            addVertex(vertices, 3, x + width / 2, y + height / 2);
            addVertex(vertices, 4, radialInterpolation(x, x + width, angle), y + height);
            //vertices[8] = x + width / 2 + (float)(height * Math.tan(angle) / 2);
            //vertices[9] = y + height;
            addVertex(vertices, 5, x + width, y + height);
            addVertex(vertices, 6, x + width, y);
        } else if (Double.compare(angle, 3 * Math.PI / 4) <= 0) {
            vertices = new float[6 * 2];
            addVertex(vertices, 0, x, y);
            addVertex(vertices, 1, x, y + height);
            addVertex(vertices, 2, x + width / 2, y + height);
            addVertex(vertices, 3, x + width / 2, y + height / 2);
            addVertex(vertices, 4, x + width, radialInterpolation(y + height, y, angle - Math.PI / 2));
            //vertices[8] = x + width;
            //if (Double.compare(angle, Math.PI / 2) <= 0) {
            //    vertices[9] = y + height / 2 + (float)(width * Math.tan(Math.PI / 2 - angle) / 2);
            //} else {
            //    vertices[9] = y + height / 2 - (float)(width * Math.tan(angle - Math.PI / 2) / 2);
            //}
            addVertex(vertices, 5, x + width, y);
        } else if (Double.compare(angle, 5 * Math.PI / 4) <= 0) {
            vertices = new float[5 * 2];
            addVertex(vertices, 0, x, y);
            addVertex(vertices, 1, x, y + height);
            addVertex(vertices, 2, x + width / 2, y + height);
            addVertex(vertices, 3, x + width / 2, y + height / 2);
            addVertex(vertices, 4, radialInterpolation(x + width, x, angle - Math.PI), y);
            //if (Double.compare(angle, Math.PI) <= 0) {
            //    vertices[8] = x + width / 2 + (float)(height * Math.tan(Math.PI - angle) / 2);
            //} else {
            //    vertices[8] = x + width / 2 - (float)(height * Math.tan(angle - Math.PI) / 2);
            //}
            //vertices[9] = y;
        } else if (Double.compare(angle, 7 * Math.PI / 4) <= 0) {
            vertices = new float[4 * 2];
            addVertex(vertices, 0, x, radialInterpolation(y, y + height, angle - 3 * Math.PI / 2));
            //vertices[0] = x;
            //if (Double.compare(angle, 3 * Math.PI / 2) <= 0) {
            //    vertices[1] = y + height / 2 - (float)(width * Math.tan(3 * Math.PI / 2 - angle) / 2);
            //} else {
            //    vertices[1] = y + height / 2 + (float)(width * Math.tan(angle - 3 * Math.PI / 2) / 2);
            //}
            addVertex(vertices, 1, x, y + height);
            addVertex(vertices, 2, x + width / 2, y + height);
            addVertex(vertices, 3, x + width / 2, y + height / 2);
        } else {
            vertices = new float[3 * 2];
            addVertex(vertices, 0, radialInterpolation(x, x + width, angle - 2 * Math.PI), y + height);
            //vertices[0] = x + width / 2 - (float)(height * Math.tan(2 * Math.PI - angle) / 2);
            //vertices[1] = y + height;
            addVertex(vertices, 1, x + width / 2, y + height);
            addVertex(vertices, 2, x + width / 2, y + height / 2);
        }
        return new Polygon(vertices);
    }

    private static float radialInterpolation(float start, float end, double angle) {
        return (start + end) / 2 + (float)((end - start) * Math.tan(angle) / 2);
    }

    private static void addVertex(float[] vertices, int index, float x, float y) {
        int i = index << 1;
        vertices[i] = x;
        vertices[i + 1] = y;
    }

}
