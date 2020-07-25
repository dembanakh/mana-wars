package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.user.UserGreetingAPI;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GreetingInteractorTest {

    private GreetingInteractor interactor;

    @Mock
    private UserGreetingAPI user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new GreetingInteractor(user);
    }

    @Test
    public void testHasUsername_True() {
        when(user.getName()).thenReturn("a");
        assertTrue(interactor.hasUsername());
    }

    @Test
    public void testHasUsername_False() {
        when(user.getName()).thenReturn(null);
        assertFalse(interactor.hasUsername());
    }

    @Test
    public void testRegisterUser_Success() {
        when(user.getName()).thenReturn(null);
        interactor.registerUser("a");

        verify(user).setName("a");
        verify(user).updateManaAmount(anyInt());
        verify(user).checkNextLevel();
    }

    @Test(expected = Exception.class)
    public void testRegisterClass_HasUsername() {
        when(user.getName()).thenReturn("a");
        interactor.registerUser("b");

        verify(user, never()).setName(anyString());
    }

}