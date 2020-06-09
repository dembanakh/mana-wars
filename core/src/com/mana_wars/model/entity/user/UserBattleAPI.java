package com.mana_wars.model.entity.user;

public interface UserBattleAPI {
    void reInitCharacteristics();

    boolean tryApplyActiveSkill(int skillIndex);
}
