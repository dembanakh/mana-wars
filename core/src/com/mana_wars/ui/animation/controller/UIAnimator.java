package com.mana_wars.ui.animation.controller;

public interface UIAnimator<T> {
    void animate(T data, float x, float y, float width, float height);
}
