package com.mana_wars.ui.animation.controller;

import java.util.Iterator;

/*
 * Adding <T, Iterator<U>> to TimeoutMap implies adding <T, U> for U.getDuration(), then
 * adding <T, next(U)> for next(U).getDuration(), and so on.
 */
public interface TimeoutMap<T, U extends Durationable> {
    void update(float delta);
    double getRemainingTime(T data);

    boolean add(T data, Iterator<U> valueIterator);
    U get(T data);
    void remove(T data);
    void clear();
    boolean containsKey(T data);
    int size();
}
