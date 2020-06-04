package com.mana_wars.model;

public final class Utility {

    public static long minsToMillis(int mins) {
        return mins * 60L * 1000;
    }

    public static <T extends Comparable<T>> T clamp(T value, T min, T max) {
        if (value.compareTo(min) < 0) return min;
        if (value.compareTo(max) > 0) return max;
        return value;
    }

    private Utility() {}

}
