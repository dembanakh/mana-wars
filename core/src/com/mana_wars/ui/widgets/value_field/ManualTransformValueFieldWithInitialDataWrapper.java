package com.mana_wars.ui.widgets.value_field;

public class ManualTransformValueFieldWithInitialDataWrapper<T, U> extends ManualTransformValueFieldWrapper<U> {

    private ManualTransformValueFieldWithInitialData<T, U> field;

    private T initialData;
    private U value;

    public void setField(ManualTransformValueFieldWithInitialData<T, U> field) {
        this.field = field;
        try {
            this.field.setInitialData(initialData);
            this.field.accept(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInitialData(T initialData) {
        this.initialData = initialData;
    }

    @Override
    public void accept(U value) throws Exception {
        this.value = value;
        if (field != null) field.accept(value);
    }
}
