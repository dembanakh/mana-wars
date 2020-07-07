package com.mana_wars.model.entity.battle;

import java.util.List;

import io.reactivex.subjects.Subject;

public interface BattleConfig {
    void init();
    void start();
    void update(float timeDelta);

    BattleParticipant getUser();
    List<BattleParticipant> getUserSide();
    List<BattleParticipant> getEnemySide();
    Subject<BattleSummaryData> getFinishBattleObservable();
    void dispose();
}
