package com.mana_wars.model.repository;

import com.mana_wars.model.db.entity.base.DBSkill;
import com.mana_wars.model.db.entity.query.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.query.UserSkill;
import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.utils.UpdateChecker;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static com.mana_wars.model.GameConstants.MAX_DAILY_SKILL_AMOUNT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DailySkillsRepositoryImplTest {

    private final static CompositeDisposable disposable = new CompositeDisposable();

    private DailySkillsRepository dailySkillsRepository;

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private SharedPreferencesRepository sharedPreferencesRepository;
    @Mock
    private UpdateChecker updateChecker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dailySkillsRepository = new DailySkillsRepositoryImpl(roomRepository,
                sharedPreferencesRepository, updateChecker);
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

        when(updateChecker.checkDailySkillsOffer()).thenReturn(Completable.complete());
        when(sharedPreferencesRepository.getDailySkillID(anyInt())).thenReturn(1);
        DBSkillWithCharacteristics dbswc = new DBSkillWithCharacteristics();
        dbswc.skill = mock(DBSkill.class);
        dbswc.characteristics = new ArrayList<>();
        when(dbswc.skill.getId()).thenReturn(1);
        when(dbswc.skill.isActive()).thenReturn(true);
        when(roomRepository.getSkillsWithCharacteristicsByIDs(Collections.singletonList(1)))
                .thenReturn(Single.just(Collections.singletonList(dbswc)));
        when(sharedPreferencesRepository.getDailySkillBought(2)).thenReturn(10);
        when(sharedPreferencesRepository.getDailySkillPrice(2)).thenReturn(40);
    }

    @Test
    public void testGetDailySkills() {
        AtomicReference<ShopSkill> shopSkill = new AtomicReference<>();
        disposable.add(dailySkillsRepository.getDailySkills().subscribe(shopSkills -> {
            if (shopSkills == null || shopSkills.size() != 1) throw new RuntimeException("Shop skills doesn't have size 1");
            shopSkill.set(shopSkills.get(0));
        }, Throwable::printStackTrace));

        assertNotNull(shopSkill.get());
        assertEquals(40, shopSkill.get().getPrice());
        assertEquals(MAX_DAILY_SKILL_AMOUNT - 10, shopSkill.get().instancesLeft());
        assertTrue(shopSkill.get().isActive());
        assertTrue(shopSkill.get().canBePurchased());
    }

    @Test
    public void testPurchaseSkill() {
        AtomicReference<ShopSkill> shopSkill = new AtomicReference<>();
        disposable.add(dailySkillsRepository.getDailySkills().subscribe(shopSkills -> {
            if (shopSkills == null || shopSkills.size() != 1) throw new RuntimeException("Shop skills doesn't have size 1");
            shopSkill.set(shopSkills.get(0));
        }, Throwable::printStackTrace));

        when(roomRepository.insertEntity(eq(new UserSkill(1, 1)), any())).thenReturn(Completable.complete());

        disposable.add(dailySkillsRepository.purchaseSkill(shopSkill.get()).subscribe());

        verify(sharedPreferencesRepository).setDailySkillBought(2, 11);
    }

    @AfterClass
    public static void teardown() {
        disposable.dispose();
    }

}