package com.mana_wars.ui.transform.base;

import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.layout_constraints.PositionConstraint;
import com.mana_wars.ui.layout_constraints.SizeConstraint;

public class TransformBuilder {

    private PositionConstraint xConstraint = new PositionConstraint() {
        @Override
        public int getAlign() {
            return Align.left;
        }

        @Override
        public float getPosition() {
            return 0;
        }
    };
    private PositionConstraint yConstraint = new PositionConstraint() {
        @Override
        public int getAlign() {
            return Align.bottom;
        }

        @Override
        public float getPosition() {
            return 0;
        }
    };

    private SizeConstraint widthConstraint = () -> 100;
    private SizeConstraint heightConstraint = () -> 100;

    public Transform build() {
        return new Transform(
                xConstraint.getPosition(),
                yConstraint.getPosition(),
                widthConstraint.getSize(),
                heightConstraint.getSize(),
                xConstraint.getAlign() | yConstraint.getAlign());
    }

    /**
     * @param constraint X position constraint. Default is 0 from the left.
     */
    public TransformBuilder setXConstraint(PositionConstraint constraint) {
        this.xConstraint = constraint;
        return this;
    }

    /**
     * @param constraint Y position constraint. Default is 0 from the bottom.
     */
    public TransformBuilder setYConstraint(PositionConstraint constraint) {
        this.yConstraint = constraint;
        return this;
    }

    /**
     * @param constraint Width constraint. Default is 100px.
     */
    public TransformBuilder setWidthConstraint(SizeConstraint constraint) {
        this.widthConstraint = constraint;
        return this;
    }

    /**
     * @param constraint Height constraint. Default is 100px.
     */
    public TransformBuilder setHeightConstraint(SizeConstraint constraint) {
        this.heightConstraint = constraint;
        return this;
    }
}
