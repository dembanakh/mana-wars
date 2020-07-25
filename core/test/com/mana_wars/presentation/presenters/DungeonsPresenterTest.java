package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.dungeon.Dungeon;
import com.mana_wars.model.interactor.DungeonsInteractor;
import com.mana_wars.presentation.view.DungeonsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DungeonsPresenterTest {

    private DungeonsPresenter presenter;

    @Mock
    private DungeonsView view;
    @Mock
    private DungeonsInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new DungeonsPresenter(view, interactor, Runnable::run);
    }

    @Test
    public void testRefreshDungeonsList() {
        List<Dungeon> dungeons = Collections.singletonList(mock(Dungeon.class));
        when(interactor.getDungeons()).thenAnswer((Answer<Single<List<Dungeon>>>)
                invocation -> Single.just(dungeons));

        presenter.refreshDungeonsList();

        verify(view).setDungeonsList(dungeons);
    }

    @Test
    public void testRefreshRequiredManaAmount() {
        when(interactor.getRequiredManaAmountForBattle()).thenAnswer((Answer<Single<Integer>>)
                invocation -> Single.just(100));
        when(interactor.getUserLevel()).thenReturn(2);
        when(interactor.getUserManaAmount()).thenReturn(50);

        presenter.refreshRequiredManaAmount();

        verify(view).disableDungeons(2, true);
    }

}