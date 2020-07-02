package com.mana_wars.ui.widgets.value_field;

import io.reactivex.functions.Consumer;

public class ManualTransformValueFieldWrapper<T> implements Consumer<T> {

    private ManualTransformValueField<T> field;
    private T value;

    public void setField(ManualTransformValueField<T> field) {
        this.field = field;
        try {
            this.field.accept(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(T value) throws Exception {
        this.value = value;
        if (field != null) field.accept(value);
    }
}
