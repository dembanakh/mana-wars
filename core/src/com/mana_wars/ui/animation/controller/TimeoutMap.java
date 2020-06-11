package com.mana_wars.ui.animation.controller;

import java.util.Iterator;

public interface TimeoutMap<T, U extends Durationable> {
    void update(float delta);
    boolean add(T data, Iterator<U> valueIterator);
    U get(T data);
    void remove(T data);
    double getRemainingTime(T data);
    boolean contains(T data);
}
