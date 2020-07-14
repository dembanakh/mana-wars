package com.mana_wars.ui.animation.shape_animator;

import com.badlogic.gdx.graphics.Color;

import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class ShapeAnimator {

    private final Color color;

    ShapeAnimator(final Color color) {
        this.color = color;
    }

    Color getColor() {
        return color;
    }

    public abstract void animate(ShapeDrawer shapeDrawer, float x, float y, float width, float height,
                                 double timeLeft, double duration);
}
