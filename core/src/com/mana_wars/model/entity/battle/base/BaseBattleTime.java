package com.mana_wars.model.entity.battle.base;

class BaseBattleTime implements BattleTime {

    private double currentTime;

    BaseBattleTime() {
        currentTime = 0;
    }

    @Override
    public void update(float delta) {
        currentTime += delta;
    }

    @Override
    public double get() {
        return currentTime;
    }
}
