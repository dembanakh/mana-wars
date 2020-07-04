package com.mana_wars.ui.widgets.value_field;

import com.mana_wars.ui.layout_constraints.PositionConstraint;
import com.mana_wars.ui.layout_constraints.SizeConstraint;

public interface ManualTransform extends ManualBackground {
    ManualTransform setXConstraint(PositionConstraint constraint);
    ManualTransform setYConstraint(PositionConstraint constraint);
    ManualTransform setWidthConstraint(SizeConstraint constraint);
    ManualTransform setHeightConstraint(SizeConstraint constraint);
}
