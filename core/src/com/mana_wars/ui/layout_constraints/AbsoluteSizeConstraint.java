package com.mana_wars.ui.layout_constraints;

public class AbsoluteSizeConstraint implements SizeConstraint {

    private final float size;

    public AbsoluteSizeConstraint(float size) {
        this.size = size;
    }

    @Override
    public float getSize() {
        return size;
    }
}
