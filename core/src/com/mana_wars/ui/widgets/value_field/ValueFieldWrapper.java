package com.mana_wars.ui.widgets.value_field;

import io.reactivex.functions.Consumer;

public class ValueFieldWrapper<T> implements Consumer<T> {

    private ValueField<T> field;
    private T value;

    public void setField(ValueField<T> field) {
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
