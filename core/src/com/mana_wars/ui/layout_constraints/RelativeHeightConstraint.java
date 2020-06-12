package com.mana_wars.ui.layout_constraints;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;

public class RelativeHeightConstraint implements SizeConstraint {

    private final int percent;

    public RelativeHeightConstraint() {
        this(100);
    }

    public RelativeHeightConstraint(int percent) {
        this.percent = percent;
    }

    @Override
    public float getSize() {
        return (percent * (float)SCREEN_HEIGHT()) / 100;
    }
}
