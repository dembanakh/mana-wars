package com.mana_wars.ui.layout_constraints;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

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
