package com.mana_wars.model.entity.battle.base;

import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import java.util.List;

import io.reactivex.Single;

public interface Battle {
    void start();
    void update(float timeDelta);
    void dispose();

    BattleParticipant getUser();
    List<BattleParticipant> getUserSide();
    List<BattleParticipant> getEnemySide();
    Single<ReadableBattleSummaryData> getFinishBattleObservable();
}
