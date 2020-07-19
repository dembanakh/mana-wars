package com.mana_wars.ui.widgets.value_field.base;

import io.reactivex.functions.Consumer;

public class ValueFieldWrapper<TInitial, TValue> implements Consumer<TValue> {

    private ValueField<TInitial, TValue> field;

    private TInitial initialData;
    private TValue value;

    public void setField(ValueField<TInitial, TValue> field) {
        this.field = field;
        if (field == null) return;
        try {
            this.field.setInitialData(initialData);
            this.field.accept(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInitialData(TInitial initialData) {
        this.initialData = initialData;
    }

    @Override
    public void accept(TValue value) throws Exception {
        this.value = value;
        if (field != null) field.accept(value);
    }
}
