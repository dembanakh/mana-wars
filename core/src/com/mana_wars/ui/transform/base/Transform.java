package com.mana_wars.ui.transform.base;

public class Transform {

    private final float x, y;
    private final float width, height;
    private final int align;

    Transform(float x, float y, float width, float height, int align) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.align = align;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getAlign() {
        return align;
    }
}
