package com.mana_wars.ui.widgets.value_field;

public abstract class ManualTransformValueFieldWithInitialData<T, U> extends ManualTransformValueField<U> {

    public abstract void setInitialData(T data);

}
