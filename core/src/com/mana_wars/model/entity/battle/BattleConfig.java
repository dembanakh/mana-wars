package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.User;

import java.util.List;

public interface BattleConfig {
    void init();
    void start();

    void update(float timeDelta);
    boolean checkFinish();
    void finish();

    User getUser();
    List<BattleParticipant> getUserSide();
    List<BattleParticipant> getEnemySide();
}
