package com.mana_wars.ui.widgets.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClickableList2DTest {

    @Mock
    private ListItemConsumer<Integer> consumer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnClick() {
        Skin skin = new Skin();
        skin.add("default", new List.ListStyle());
        ClickableList2D<Integer> list = new ClickableList2D<>(skin,
                (batch, font, index, item, x, y, width, height) -> {}, 5, consumer);
        list.setItems(10, 20, 30, 40);

        InputEvent event = mock(InputEvent.class);
        when(event.getType()).thenReturn(InputEvent.Type.touchDown);
        when(event.getPointer()).thenReturn(0);
        when(event.getButton()).thenReturn(0);
        when(event.toCoordinates(any(), any())).thenAnswer((Answer<Vector2>) invocation -> {
            invocation.getArgumentAt(1, Vector2.class).set(0, 0);
            return new Vector2(0, 0);
        });
        list.getListeners().get(1).handle(event);

        verify(consumer).accept(10, 0);
    }

}