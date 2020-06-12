package com.mana_wars.ui.layout_constraints;

import com.badlogic.gdx.utils.Align;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class AbsoluteXPositionConstraint implements PositionConstraint {

    private final int align;
    private final float offset;

    public AbsoluteXPositionConstraint(int align, float offset) throws IllegalArgumentException {
        if ((align & (Align.left | Align.right)) == 0)
            throw new IllegalArgumentException("This align is not legal for xConstraint");
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
            case Align.left:
                return offset;
            case Align.right:
                return SCREEN_WIDTH() - offset;
            default:
                return 0;
        }
    }
}
