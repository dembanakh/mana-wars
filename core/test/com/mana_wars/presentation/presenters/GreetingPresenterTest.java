package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.GreetingInteractor;
import com.mana_wars.presentation.view.GreetingView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GreetingPresenterTest {

    private GreetingPresenter presenter;

    @Mock
    private GreetingView view;
    @Mock
    private GreetingInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new GreetingPresenter(view, interactor, Runnable::run);
    }

    @Test
    public void testIsFirstTimeAppOpen_True() {
        when(interactor.hasUsername()).thenReturn(false);
        assertTrue(presenter.isFirstTimeAppOpen());
    }

    @Test
    public void testIsFirstTimeAppOpen_False() {
        when(interactor.hasUsername()).thenReturn(true);
        assertFalse(presenter.isFirstTimeAppOpen());
    }

    @Test
    public void testOnStart() {
        presenter.onStart("a");

        verify(interactor).registerUser("a");
        verify(view).onStart();
    }

}