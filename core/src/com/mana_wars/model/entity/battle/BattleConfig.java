package com.mana_wars.model.entity.battle;

import java.util.List;

public interface BattleConfig {
    void init();
    void start();

    void update(float timeDelta);
    boolean checkFinish();
    void finish();

    BattleParticipant getUser();
    List<BattleParticipant> getUserSide();
    List<BattleParticipant> getEnemySide();
}
