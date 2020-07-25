package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.model.interactor.ShopInteractor;
import com.mana_wars.presentation.view.ShopView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import io.reactivex.Single;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShopPresenterTest {

    private ShopPresenter presenter;

    @Mock
    private ShopView view;
    @Mock
    private ShopInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new ShopPresenter(view, interactor, Runnable::run);
    }

    @Test
    public void testBuyOneSkillCase() {
        when(interactor.buySkillCases(1, 1)).thenReturn(100);
        when(interactor.buySkillCases(not(eq(1)), anyInt())).thenThrow(new RuntimeException("wrong BuyOneSkillCase"));

        presenter.buyOneSkillCase();

        verify(view).setSkillCasesNumber(100);
    }

    @Test
    public void testBuyTenSkillCases() {
        when(interactor.buySkillCases(eq(10), anyInt())).thenReturn(100);
        when(interactor.buySkillCases(not(eq(10)), anyInt())).thenThrow(new RuntimeException("wrong BuyOneSkillCase"));

        presenter.buyTenSkillCases();

        verify(view).setSkillCasesNumber(100);
    }

    // Test onOpenSkillCase and refreshSkillCasesNumber - the same as in MainMenuPresenter

    @Test
    public void testRefreshPurchasableSkills() {
        ShopSkill shopSkill = mock(ShopSkill.class);
        when(interactor.getPurchasableSkills()).thenReturn(Single.just(Collections.singletonList(shopSkill)));

        presenter.refreshPurchasableSkills();

        verify(view).setPurchasableSkills(Collections.singletonList(shopSkill));
    }

}