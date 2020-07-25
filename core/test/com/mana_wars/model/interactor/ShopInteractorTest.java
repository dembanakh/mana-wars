package com.mana_wars.model.interactor;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.model.entity.user.UserShopAPI;
import com.mana_wars.model.repository.DailySkillsRepository;
import com.mana_wars.model.repository.DatabaseRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ShopInteractorTest {

    private ShopInteractor interactor;

    @Mock
    private UserShopAPI user;
    @Mock
    private DatabaseRepository databaseRepository;
    @Mock
    private DailySkillsRepository dailySkillsRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new ShopInteractor(user, databaseRepository, dailySkillsRepository);
    }

    @Test
    public void testPurchaseSkill_Success() {
        ShopSkill shopSkill = mock(ShopSkill.class);
        when(shopSkill.canBePurchased()).thenReturn(true);
        when(shopSkill.getPrice()).thenReturn(100);
        when(dailySkillsRepository.purchaseSkill(shopSkill)).thenReturn(Completable.complete());

        interactor.purchaseSkill(shopSkill);

        verify(user).updateManaAmount(-100);
    }

    @Test
    public void testPurchaseSkill_Failure() {
        ShopSkill shopSkill = mock(ShopSkill.class);
        when(shopSkill.canBePurchased()).thenReturn(false);

        interactor.purchaseSkill(shopSkill);

        verifyNoMoreInteractions(user);
        verifyNoMoreInteractions(dailySkillsRepository);
    }

    @Test
    public void testBuySkillCases() {
        when(user.updateSkillCases(5)).thenReturn(100);
        when(user.updateSkillCases(not(eq(5)))).thenThrow(new RuntimeException("skill cases were not increased by 5"));

        interactor.buySkillCases(5, 2);

        verify(user).updateManaAmount(-2 * GameConstants.SKILL_CASE_PRICE);
    }

}