package com.mana_wars.ui.transform;

import com.mana_wars.ui.transform.base.Transform;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;

public final class TransformFactory {

    public static TransformApplier manualTransform(Transform transform) {
        return new ManualTransformController(transform);
    }

    public static TransformApplier autoTransform() {
        return new AutoTransformController();
    }

}
