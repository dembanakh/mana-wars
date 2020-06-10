package com.mana_wars.ui.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Selected items must be different (Set).
 */
public class TimeoutSelection<T> {

    private final Set<TimeoutItem<T>> selection;

    public TimeoutSelection() {
        selection = new HashSet<>();
    }

    public void update(float delta) {
        List<TimeoutItem<T>> toRemove = new ArrayList<>();
        for (TimeoutItem<T> item : selection) {
            item.time -= delta;
            if (item.time < 0) {
                item.time = 0;
                toRemove.add(item);
            }
        }
        deselectAll(toRemove);
    }

    private void deselectAll(Iterable<TimeoutItem<T>> items) {
        for (TimeoutItem<T> item : items) {
            selection.remove(item);
        }
    }

    public void selectFor(T data, double time) {
        selection.add(new TimeoutItem<>(data, Math.max(getRemainingTime(data), time)));
    }

    public double getRemainingFraction(T data) {
        for (TimeoutItem<T> item : selection) {
            if (item.data == data) return item.time / item.initialTime;
        }
        return 0;
    }

    private double getRemainingTime(T data) {
        for (TimeoutItem<T> item : selection) {
            if (item.data == data) return item.time;
        }
        return 0;
    }

    public boolean contains(T data) {
        for (TimeoutItem<T> item : selection) {
            if (item.data == data) return true;
        }
        return false;
    }

    private static class TimeoutItem<T> {
        private final T data;
        private final double initialTime;
        private double time;

        private TimeoutItem(T data, double initialTime) {
            this.data = data;
            this.initialTime = initialTime;
            this.time = initialTime;
        }
    }

}
