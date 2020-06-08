package com.mana_wars.ui.widgets.value_field;

import com.mana_wars.ui.layout_constraints.PositionConstraint;
import com.mana_wars.ui.layout_constraints.SizeConstraint;

public abstract class ValueFieldWithInitialData<T, U> extends ValueField<U> {

    public abstract void setInitialData(T data);

    public ValueFieldWithInitialData<T, U> setBackgroundColor(String backgroundColor) {
        super.setBackgroundColor(backgroundColor);
        return this;
    }

    public ValueFieldWithInitialData<T, U> setXConstraint(PositionConstraint constraint) {
        super.setXConstraint(constraint);
        return this;
    }

    public ValueFieldWithInitialData<T, U> setYConstraint(PositionConstraint constraint) {
        super.setYConstraint(constraint);
        return this;
    }

    public ValueFieldWithInitialData<T, U> setWidthConstraint(SizeConstraint constraint) {
        super.setWidthConstraint(constraint);
        return this;
    }

    public ValueFieldWithInitialData<T, U> setHeightConstraint(SizeConstraint constraint) {
        super.setHeightConstraint(constraint);
        return this;
    }

}
