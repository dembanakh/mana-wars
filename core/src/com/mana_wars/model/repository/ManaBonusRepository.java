package com.mana_wars.model.repository;

public interface ManaBonusRepository {
    boolean wasBonusEverClaimed();
    long getLastTimeBonusClaimed();
    void setLastTimeBonusClaimed(long time);
}
