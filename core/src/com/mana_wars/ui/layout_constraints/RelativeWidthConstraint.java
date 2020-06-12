package com.mana_wars.ui.layout_constraints;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class RelativeWidthConstraint implements SizeConstraint {

    private final int percent;

    public RelativeWidthConstraint() {
        this(100);
    }

    public RelativeWidthConstraint(int percent) {
        this.percent = percent;
    }

    @Override
    public float getSize() {
        return (percent * (float)SCREEN_WIDTH()) / 100;
    }
}
