package com.mana_wars.ui.animation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Selected items must be different in terms of equals() (Map).
 */
public class TimeoutHashMap<T, U extends Durationable> implements TimeoutMap<T, U> {

    private final Map<T, TimeoutItem<U>> map;

    TimeoutHashMap() {
        map = new HashMap<>();
    }

    @Override
    public void update(float delta) {
        List<T> timeoutItems = new ArrayList<>();
        for (Map.Entry<T, TimeoutItem<U>> entry : map.entrySet()) {
            entry.getValue().time -= delta;
            if (entry.getValue().time <= 0) {
                entry.getValue().time = 0;
                timeoutItems.add(entry.getKey());
            }
        }
        processTimeoutItems(timeoutItems);
    }

    private void processTimeoutItems(Iterable<T> items) {
        for (T data : items) onTimeout(data);
    }

    private void onTimeout(T data) {
        if (!add(data, map.get(data).dataIterator))
            map.remove(data);
    }

    @Override
    public boolean add(T data, Iterator<U> valueIterator) {
        if (!valueIterator.hasNext()) return false;
        map.put(data, new TimeoutItem<>(valueIterator.next(), valueIterator));
        return true;
    }

    @Override
    public U get(T data) {
        if (map.containsKey(data))
            return map.get(data).data;
        return null;
    }

    @Override
    public void remove(T data) {
        map.remove(data);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public double getRemainingTime(T data) {
        if (map.containsKey(data))
            return map.get(data).time;
        return 0;
    }

    @Override
    public boolean containsKey(T data) {
        return map.containsKey(data);
    }

    @Override
    public int size() {
        return map.size();
    }

    private static class TimeoutItem<U extends Durationable> {
        private final U data;
        private final Iterator<U> dataIterator;
        private double time;

        private TimeoutItem(U data, Iterator<U> dataIterator) {
            this.data = data;
            this.dataIterator = dataIterator;
            this.time = data.getDuration();
        }
    }

}
