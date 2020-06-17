package com.mana_wars.model.entity.base;

public enum ValueType {
    PERCENT {
        @Override
        public int apply(int prev, ValueChangeType type, int diff) {
            return (int)(prev * (1 + (type.getConstant() * diff) / 100f));
        }
    },
    VALUE {
        @Override
        public int apply(int prev, ValueChangeType type, int diff) {
            return prev + type.getConstant() * diff;
        }
    };

    public abstract int apply(int prev, ValueChangeType type, int diff);
}
