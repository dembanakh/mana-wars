package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.layout_constraints.PositionConstraint;
import com.mana_wars.ui.layout_constraints.SizeConstraint;
import com.mana_wars.ui.widgets.BuildableUI;

import io.reactivex.functions.Consumer;

public abstract class ValueField<T> implements BuildableUI, Consumer<T> {

    private Table field;

    @Override
    public void init() {
        field = new Table();
        field.setTransform(true);

        field.setSize(getWidth(), getHeight());
        field.setPosition(getX(), getY(), getAlign());
    }

    protected void addActor(Actor actor) {
        field.add(actor).center().row();
    }

    @Override
    public Actor build(final Skin skin) {
        field.setSkin(skin);
        if (backgroundColor != null) field.setBackground(backgroundColor);

        return field;
    }


    private String backgroundColor;

    private PositionConstraint xConstraint = new PositionConstraint() {
        @Override
        public int getAlign() {
            return Align.left;
        }

        @Override
        public float getPosition() {
            return 0;
        }
    };
    private PositionConstraint yConstraint = new PositionConstraint() {
        @Override
        public int getAlign() {
            return Align.bottom;
        }

        @Override
        public float getPosition() {
            return 0;
        }
    };

    private SizeConstraint widthConstraint = () -> 100;
    private SizeConstraint heightConstraint = () -> 100;

    public ValueField<T> setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public ValueField<T> setXConstraint(PositionConstraint constraint) {
        this.xConstraint = constraint;
        return this;
    }

    public ValueField<T> setYConstraint(PositionConstraint constraint) {
        this.yConstraint = constraint;
        return this;
    }

    public ValueField<T> setWidthConstraint(SizeConstraint constraint) {
        this.widthConstraint = constraint;
        return this;
    }

    public ValueField<T> setHeightConstraint(SizeConstraint constraint) {
        this.heightConstraint = constraint;
        return this;
    }

    private float getX() {
        return xConstraint.getPosition();
    }

    private float getY() {
        return yConstraint.getPosition();
    }

    private float getWidth() {
        return widthConstraint.getSize();
    }

    private float getHeight() {
        return heightConstraint.getSize();
    }

    private int getAlign() {
        return xConstraint.getAlign() | yConstraint.getAlign();
    }

}
