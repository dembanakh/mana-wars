package com.mana_wars.ui.transform;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.transform.base.Transform;

final class ManualTransformController implements TransformApplier {

    private final Transform transform;

    ManualTransformController(Transform transform) {
        this.transform = transform;
    }

    @Override
    public void applyTransform(Group group) {
        group.setTransform(true);
        group.setSize(transform.getWidth(), transform.getHeight());
        group.setPosition(transform.getX(), transform.getY());
    }
}
