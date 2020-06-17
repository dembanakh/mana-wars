package com.mana_wars.ui.animation.controller;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface UIAnimationController<T, Type> {
    void initBatch(Batch batch);
    void clear();
    void add(T data, Iterable<KeyFrame<Type>> keyFrames);
    void update(float delta);
    void animate(T data, float x, float y, float width, float height);
    boolean contains(T data);

    class KeyFrame<Type> implements Durationable {
        public final Type type;
        private final double duration;

        public KeyFrame(final Type type, final double duration) {
            this.type = type;
            this.duration = duration;
        }

        public double getDuration() {
            return duration;
        }
    }
}
