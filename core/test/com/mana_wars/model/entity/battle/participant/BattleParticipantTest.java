package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattleParticipantTest {

    private static CompositeDisposable disposable = new CompositeDisposable();

    private BattleParticipant bp;

    @Mock
    private BattleClientAPI battle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bp = new BattleParticipant("a",
                "player",
                100,
                Collections.emptyList(),
                Collections.emptyList(),
                1, 2, 3) {
            @Override
            public void update(double currentTime) {}
        };
        bp.setBattleClientAPI(battle);
    }

    @Test
    public void testStart() {
        BattleParticipant opponent = mock(BattleParticipant.class);
        when(opponent.isAlive()).thenReturn(true);
        when(battle.getOpponents(bp)).thenReturn(Collections.singletonList(opponent));
        TestScheduler scheduler = new TestScheduler();
        AtomicInteger initialHealth = new AtomicInteger();
        disposable.add(bp.getHealthObservable().observeOn(scheduler).subscribe(initialHealth::set));

        bp.start();
        scheduler.triggerActions();

        assertEquals(100, initialHealth.get());
    }

    @Test
    public void testApplySkillCharacteristicHEALTH() {
        SkillCharacteristic sc = mock(SkillCharacteristic.class);
        when(sc.getCharacteristic()).thenReturn(Characteristic.HEALTH);
        when(sc.isHealth()).thenReturn(true);
        when(sc.getValue(1)).thenReturn(10);
        when(sc.getChangeType()).thenReturn(ValueChangeType.DECREASE);
        TestScheduler scheduler = new TestScheduler();
        AtomicInteger health = new AtomicInteger();
        disposable.add(bp.getHealthObservable().observeOn(scheduler).subscribe(health::set));

        bp.applySkillCharacteristic(sc, 1);
        scheduler.triggerActions();

        assertEquals(90, health.get());
    }

    @AfterClass
    public static void clean() {
        disposable.dispose();
    }
}