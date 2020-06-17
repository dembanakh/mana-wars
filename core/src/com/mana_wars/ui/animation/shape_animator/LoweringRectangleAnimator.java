package com.mana_wars.ui.animation.shape_animator;

import com.badlogic.gdx.graphics.Color;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class LoweringRectangleAnimator extends ShapeAnimator {
    public LoweringRectangleAnimator(final Color color) {
        super(color);
    }

    @Override
    public void animate(ShapeDrawer shapeDrawer, float x, float y, float width, float height, double timeLeft, double duration) {
        shapeDrawer.setColor(getColor());
        shapeDrawer.filledRectangle(x, y, width, (float) (height * (timeLeft / duration)));
    }
}
