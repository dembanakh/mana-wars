package com.mana_wars.model.mana_bonus;

import com.mana_wars.model.repository.ManaBonusRepository;

import io.reactivex.functions.Consumer;

public interface ManaBonus {
    void init();
    long getTimeSinceLastClaim(); //millis
    boolean isBonusBitAvailable();
    void onBonusClaimed(Consumer<? super Integer> callback);

    int getFullBonusTimeout(); //minutes
}
