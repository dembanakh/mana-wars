package com.mana_wars.model.mana_bonus;

public interface ManaBonus {
    void init();
    long getTimeSinceLastClaim(); //millis

    /**
     * @return true if the first bonus bit is ready to be claimed
     */
    boolean isBonusBitAvailable();
    int evalCurrentBonus();
    void onBonusClaimed();
    int getFullBonusTimeout(); //minutes
}
