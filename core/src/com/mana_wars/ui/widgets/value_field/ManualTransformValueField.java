package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.layout_constraints.PositionConstraint;
import com.mana_wars.ui.layout_constraints.SizeConstraint;

public abstract class ManualTransformValueField<T> implements ManualTransform, ValueField<T> {

    private Table field;

    @Override
    public void init() {
        field = new Table();

        field.setTransform(true);
        field.setSize(getWidth(), getHeight());
        field.setPosition(getX(), getY(), getAlign());
    }

    @Override
    public Actor build(final Skin skin) {
        field.setSkin(skin);
        if (backgroundColor != UIStringConstants.UI_SKIN.BACKGROUND_COLOR.NONE)
            field.setBackground(backgroundColor.toString());

        return field;
    }

    protected void addActorAndExpandX(Actor actor) {
        field.add(actor).top().width(getWidth()).row();
    }

    protected void addActor(Actor actor) {
        field.add(actor).top().row();
    }

    protected void addActorAndPad(Actor actor, float pad) {
        field.add(actor).pad(pad).top().row();
    }


    private UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor = UIStringConstants.UI_SKIN.BACKGROUND_COLOR.NONE;

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

    @Override
    public ManualTransformValueField<T> setBackgroundColor(UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    @Override
    public ManualTransformValueField<T> setXConstraint(PositionConstraint constraint) {
        this.xConstraint = constraint;
        return this;
    }

    @Override
    public ManualTransformValueField<T> setYConstraint(PositionConstraint constraint) {
        this.yConstraint = constraint;
        return this;
    }

    @Override
    public ManualTransformValueField<T> setWidthConstraint(SizeConstraint constraint) {
        this.widthConstraint = constraint;
        return this;
    }

    @Override
    public ManualTransformValueField<T> setHeightConstraint(SizeConstraint constraint) {
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
