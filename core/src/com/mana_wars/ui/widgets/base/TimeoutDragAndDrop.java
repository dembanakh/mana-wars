package com.mana_wars.ui.widgets.base;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * An extension of the original DragAndDrop implementation in Libgdx.
 * A drag is valid only if it started after some time after touchDown.
 */
public class TimeoutDragAndDrop {
    private static final Vector2 tmpVector = new Vector2();

    private TimeoutDragAndDrop.Source dragSource;
    private TimeoutDragAndDrop.Payload payload;
    private Actor dragActor;
    private TimeoutDragAndDrop.Target target;
    private boolean isValidTarget;
    private final Array<TimeoutDragAndDrop.Target> targets = new Array<>();
    private final ObjectMap<TimeoutDragAndDrop.Source, DragListener> sourceListeners = new ObjectMap<>();
    private float tapSquareSize = 8;
    private int button;
    private float dragActorX = 0;
    private float dragActorY = 0;
    private float touchOffsetX;
    private float touchOffsetY;
    private long dragValidTime;
    private int dragTime = 250;
    private int activePointer = -1;
    private boolean cancelTouchFocus = true;
    private boolean keepWithinStage = true;

    private final float touchDownTimeout;

    /*
     * touchDownTimeout - time in seconds for the user to keep finger down until the drag starts
     */
    public TimeoutDragAndDrop(float touchDownTimeout) {
        this.touchDownTimeout = touchDownTimeout;
    }

    public void update(float delta) {
        for (Source source : sourceListeners.keys()) {
            if (source.touchedDown) {
                source.timeSinceTouchDown += delta;
                if (source.timeSinceTouchDown >= touchDownTimeout) {
                    source.touchedDown = false;
                    source.timeSinceTouchDown = 0f;
                    sourceListeners.get(source).dragStart(new InputEvent(), 0f, 0f, source.touchDownPointer);
                }
            }
        }
    }

    public void addSource(final TimeoutDragAndDrop.Source source) {
        DragListener listener = new DragListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean result = super.touchDown(event, x, y, pointer, button);
                if (!result) return false;
                source.touchedDown = true;
                source.timeSinceTouchDown = 0f;
                source.touchDownPointer = pointer;
                return true;
            }

            public void dragStart(InputEvent event, float x, float y, int pointer) {
                if (activePointer != -1) {
                    event.stop();
                    return;
                }
                if (source.touchedDown) {
                    source.touchedDown = false;
                    source.timeSinceTouchDown = 0f;
                    event.stop();
                    return;
                }

                activePointer = pointer;

                dragValidTime = System.currentTimeMillis() + dragTime;
                dragSource = source;
                payload = source.dragStart(event, getTouchDownX(), getTouchDownY(), pointer);
                event.stop();

                if (cancelTouchFocus && payload != null) {
                    Stage stage = source.getActor().getStage();
                    if (stage != null) stage.cancelTouchFocusExcept(this, source.getActor());
                }

                if (payload != null) {
                    // Add and position the drag actor.
                    Stage stage = event.getStage();
                    event.setStage(source.getActor().getStage());
                    event.setStageX(getStageTouchDownX());
                    event.setStageY(getStageTouchDownY());
                    drag(event, getTouchDownX(), getTouchDownY(), pointer);
                    if (stage == null) source.forceDragStarted = true;
                }
            }

            public void drag(InputEvent event, float x, float y, int pointer) {
                if (payload == null) return;
                if (pointer != activePointer) return;

                if (source.forceDragStarted) source.forceDragStarted = false;

                source.drag(event, x, y, pointer);

                Stage stage = event.getStage();

                if (dragActor != null) {
                    dragActor.remove(); // Remove so it cannot be hit (Touchable.disabled isn't enough).
                    dragActor = null;
                }

                // Find target.
                TimeoutDragAndDrop.Target newTarget = null;
                isValidTarget = false;
                float stageX = event.getStageX() + touchOffsetX, stageY = event.getStageY() + touchOffsetY;
                Actor hit = event.getStage().hit(stageX, stageY, true); // Prefer touchable actors.
                if (hit == null) hit = event.getStage().hit(stageX, stageY, false);
                if (hit != null) {
                    for (int i = 0, n = targets.size; i < n; i++) {
                        TimeoutDragAndDrop.Target target = targets.get(i);
                        if (!target.actor.isAscendantOf(hit)) continue;
                        newTarget = target;
                        target.actor.stageToLocalCoordinates(tmpVector.set(stageX, stageY));
                        break;
                    }
                }
                // If over a new target, notify the former target that it's being left behind.
                if (newTarget != target) {
                    if (target != null) target.reset(source, payload);
                    target = newTarget;
                }
                // Notify new target of drag.
                if (newTarget != null)
                    isValidTarget = newTarget.drag(source, payload, tmpVector.x, tmpVector.y, pointer);

                // Add and position the drag actor.
                Actor actor = null;
                if (target != null)
                    actor = isValidTarget ? payload.validDragActor : payload.invalidDragActor;
                if (actor == null) actor = payload.dragActor;
                dragActor = actor;
                if (actor == null) return;
                stage.addActor(actor);
                float actorX = event.getStageX() - actor.getWidth() + dragActorX;
                float actorY = event.getStageY() + dragActorY;
                if (keepWithinStage) {
                    if (actorX < 0) actorX = 0;
                    if (actorY < 0) actorY = 0;
                    if (actorX + actor.getWidth() > stage.getWidth())
                        actorX = stage.getWidth() - actor.getWidth();
                    if (actorY + actor.getHeight() > stage.getHeight())
                        actorY = stage.getHeight() - actor.getHeight();
                }
                actor.setPosition(actorX, actorY);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (pointer != activePointer) return;
                activePointer = -1;
                if (payload == null) return;

                if (System.currentTimeMillis() < dragValidTime) isValidTarget = false;
                if (dragActor != null) dragActor.remove();
                if (isValidTarget) {
                    float stageX = event.getStageX() + touchOffsetX, stageY = event.getStageY() + touchOffsetY;
                    target.actor.stageToLocalCoordinates(tmpVector.set(stageX, stageY));
                    target.drop(source, payload, tmpVector.x, tmpVector.y, pointer);
                }
                source.dragStop(event, x, y, pointer, payload, isValidTarget ? target : null);
                if (target != null) target.reset(source, payload);
                dragSource = null;
                payload = null;
                target = null;
                isValidTarget = false;
                dragActor = null;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (source.forceDragStarted) dragStop(event, x, y, source.touchDownPointer);
                source.forceDragStarted = false;
                source.touchedDown = false;
            }
        };
        listener.setTapSquareSize(tapSquareSize);
        listener.setButton(button);
        source.actor.addCaptureListener(listener);
        sourceListeners.put(source, listener);
    }

    public void removeSource(TimeoutDragAndDrop.Source source) {
        DragListener dragListener = sourceListeners.remove(source);
        source.actor.removeCaptureListener(dragListener);
    }

    public void addTarget(TimeoutDragAndDrop.Target target) {
        targets.add(target);
    }

    public void removeTarget(TimeoutDragAndDrop.Target target) {
        targets.removeValue(target, true);
    }

    /**
     * Removes all targets and sources.
     */
    public void clear() {
        targets.clear();
        for (ObjectMap.Entry<TimeoutDragAndDrop.Source, DragListener> entry : sourceListeners.entries())
            entry.key.actor.removeCaptureListener(entry.value);
        sourceListeners.clear();
    }

    /**
     * Cancels the touch focus for everything except the specified source.
     */
    public void cancelTouchFocusExcept(TimeoutDragAndDrop.Source except) {
        DragListener listener = sourceListeners.get(except);
        if (listener == null) return;
        Stage stage = except.getActor().getStage();
        if (stage != null) stage.cancelTouchFocusExcept(listener, except.getActor());
    }

    /**
     * Sets the distance a touch must travel before being considered a drag.
     */
    public void setTapSquareSize(float halfTapSquareSize) {
        tapSquareSize = halfTapSquareSize;
    }

    /**
     * Sets the button to listen for, all other buttons are ignored. Default is {@link Input.Buttons#LEFT}. Use -1 for any button.
     */
    public void setButton(int button) {
        this.button = button;
    }

    public void setDragActorPosition(float dragActorX, float dragActorY) {
        this.dragActorX = dragActorX;
        this.dragActorY = dragActorY;
    }

    /**
     * Sets an offset in stage coordinates from the touch position which is used to determine the drop location. Default is
     * 0,0.
     */
    public void setTouchOffset(float touchOffsetX, float touchOffsetY) {
        this.touchOffsetX = touchOffsetX;
        this.touchOffsetY = touchOffsetY;
    }

    public boolean isDragging() {
        return payload != null;
    }

    /**
     * Returns the current drag actor, or null.
     */
    public Actor getDragActor() {
        return dragActor;
    }

    /**
     * Returns the current drag payload, or null.
     */
    public TimeoutDragAndDrop.Payload getDragPayload() {
        return payload;
    }

    /**
     * Returns the current drag source, or null.
     */
    public TimeoutDragAndDrop.Source getDragSource() {
        return dragSource;
    }

    /**
     * Time in milliseconds that a drag must take before a drop will be considered valid. This ignores an accidental drag and drop
     * that was meant to be a click. Default is 250.
     */
    public void setDragTime(int dragMillis) {
        this.dragTime = dragMillis;
    }

    public int getDragTime() {
        return dragTime;
    }

    /**
     * Returns true if a drag is in progress and the {@link #setDragTime(int) drag time} has elapsed since the drag started.
     */
    public boolean isDragValid() {
        return payload != null && System.currentTimeMillis() >= dragValidTime;
    }

    /**
     * When true (default), the {@link Stage#cancelTouchFocus()} touch focus} is cancelled if
     * {@link TimeoutDragAndDrop.Source#dragStart(InputEvent, float, float, int) dragStart} returns non-null. This ensures the DragAndDrop is the only
     * touch focus listener, eg when the source is inside a {@link ScrollPane} with flick scroll enabled.
     */
    public void setCancelTouchFocus(boolean cancelTouchFocus) {
        this.cancelTouchFocus = cancelTouchFocus;
    }

    public void setKeepWithinStage(boolean keepWithinStage) {
        this.keepWithinStage = keepWithinStage;
    }

    /**
     * A source where a payload can be dragged from.
     *
     * @author Nathan Sweet
     */
    static abstract public class Source {
        final Actor actor;

        private boolean forceDragStarted = false;
        private boolean touchedDown = false;
        private float timeSinceTouchDown = 0;
        private int touchDownPointer;

        protected Source(Actor actor) {
            if (actor == null) throw new IllegalArgumentException("actor cannot be null.");
            this.actor = actor;
        }

        /**
         * Called when a drag is started on the source. The coordinates are in the source's local coordinate system.
         *
         * @return If null the drag will not affect any targets.
         */
        abstract public TimeoutDragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer);

        /**
         * Called repeatedly during a drag which started on this source.
         */
        void drag(InputEvent event, float x, float y, int pointer) {
        }

        /**
         * Called when a drag for the source is stopped. The coordinates are in the source's local coordinate system.
         *
         * @param payload null if dragStart returned null.
         * @param target  null if not dropped on a valid target.
         */
        public void dragStop(InputEvent event, float x, float y, int pointer, TimeoutDragAndDrop.Payload payload, TimeoutDragAndDrop.Target target) {
        }

        public Actor getActor() {
            return actor;
        }
    }

    /**
     * A target where a payload can be dropped to.
     *
     * @author Nathan Sweet
     */
    static abstract public class Target {
        final Actor actor;

        public Target(Actor actor) {
            if (actor == null) throw new IllegalArgumentException("actor cannot be null.");
            this.actor = actor;
            Stage stage = actor.getStage();
            if (stage != null && actor == stage.getRoot())
                throw new IllegalArgumentException("The stage root cannot be a drag and drop target.");
        }

        /**
         * Called when the payload is dragged over the target. The coordinates are in the target's local coordinate system.
         *
         * @return true if this is a valid target for the payload.
         */
        abstract public boolean drag(TimeoutDragAndDrop.Source source, TimeoutDragAndDrop.Payload payload, float x, float y, int pointer);

        /**
         * Called when the payload is no longer over the target, whether because the touch was moved or a drop occurred. This is
         * called even if {@link #drag(TimeoutDragAndDrop.Source, TimeoutDragAndDrop.Payload, float, float, int)} returned false.
         */
        void reset(TimeoutDragAndDrop.Source source, TimeoutDragAndDrop.Payload payload) {
        }

        /**
         * Called when the payload is dropped on the target. The coordinates are in the target's local coordinate system. This is
         * not called if {@link #drag(TimeoutDragAndDrop.Source, TimeoutDragAndDrop.Payload, float, float, int)} returned false.
         */
        abstract public void drop(TimeoutDragAndDrop.Source source, TimeoutDragAndDrop.Payload payload, float x, float y, int pointer);

        public Actor getActor() {
            return actor;
        }
    }

    /**
     * The payload of a drag and drop operation. Actors can be optionally provided to follow the cursor and change when over a
     * target. Such Actors will be added and removed from the stage automatically during the drag operation. Care should be taken
     * when using the source Actor as a payload drag actor.
     */
    static public class Payload {
        Actor dragActor, validDragActor, invalidDragActor;
        Object object1;
        Object object2;

        public void setDragActor(Actor dragActor) {
            this.dragActor = dragActor;
        }

        public Actor getDragActor() {
            return dragActor;
        }

        public void setValidDragActor(Actor validDragActor) {
            this.validDragActor = validDragActor;
        }

        public Actor getValidDragActor() {
            return validDragActor;
        }

        public void setInvalidDragActor(Actor invalidDragActor) {
            this.invalidDragActor = invalidDragActor;
        }

        public Actor getInvalidDragActor() {
            return invalidDragActor;
        }

        public Object getObject1() {
            return object1;
        }

        public void setObject1(Object object1) {
            this.object1 = object1;
        }

        public Object getObject2() {
            return object2;
        }

        public void setObject2(Object object2) {
            this.object2 = object2;
        }
    }
}

