package com.mana_wars.ui.layout_constraints;

import com.badlogic.gdx.utils.Align;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;

public class AbsoluteYPositionConstraint implements PositionConstraint {

    private final int align;
    private final float offset;

    public AbsoluteYPositionConstraint(int align, float offset) {
        if ((align & (Align.top | Align.bottom)) == 0)
            throw new IllegalArgumentException("This align is not legal for yConstraint");
        this.align = align;
        this.offset = offset;
    }

    @Override
    public int getAlign() {
        return align;
    }

    @Override
    public float getPosition() {
        switch (align) {
            case Align.bottom:
                return offset;
            case Align.top:
                return SCREEN_HEIGHT - offset;
            default:
                return 0;
        }
    }

}
