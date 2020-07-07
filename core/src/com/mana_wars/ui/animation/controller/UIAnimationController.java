package com.mana_wars.ui.animation.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public interface UIAnimationController<T, Type> {
    void initBatch(Batch batch, BitmapFont font);
    void clear();
    void add(T data, Iterable<KeyFrame<Type>> keyFrames);
    void update(float delta);

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
