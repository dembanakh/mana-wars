package com.mana_wars.model.entity.base;

public enum UpgradeFunction {
    LINEAR {
        @Override
        protected double func(int value, int level) {
            return value * (level - 1);
        }
    };

    protected abstract double func(int value, int level);

    public final int apply(int value, int level, double alpha) {
        return value + (int) (alpha * func(value, level));
    }

}
