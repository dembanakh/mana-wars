package com.mana_wars.ui.widgets.base;

@FunctionalInterface
public interface ListItemConsumer<T> {
    void accept(T item, int index);
}
