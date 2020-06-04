package com.mana_wars.model.mana_bonus;

import com.badlogic.gdx.math.MathUtils;
import com.mana_wars.model.repository.ManaBonusRepository;

import io.reactivex.functions.Consumer;

import static com.mana_wars.model.Utility.clamp;
import static com.mana_wars.model.Utility.minsToMillis;

public class ManaBonusImpl implements ManaBonus {

    private final Timer timer;
    private final int bonusBitTimeout;
    private final int bonusBitSize;
    private final int numBonusBits;

    private final ManaBonusRepository manaBonusRepository;

    private boolean wasInitialized = false;

    public ManaBonusImpl(int bonusBitTimeout, int bonusBitSize, int numBonusBits, final Timer timer,
                  final ManaBonusRepository manaBonusRepository) {
        this.bonusBitTimeout = bonusBitTimeout;
        this.bonusBitSize = bonusBitSize;
        this.numBonusBits = numBonusBits;
        this.timer = timer;
        this.manaBonusRepository = manaBonusRepository;
    }

    @Override
    public void init() {
        if (!wasInitialized && !manaBonusRepository.wasBonusEverClaimed()) {
            wasInitialized = true;
            manaBonusRepository.setLastTimeBonusClaimed(timer.getCurrentTime());
        }
    }

    @Override
    public long getTimeSinceLastClaim() {
        return timer.getCurrentTime() - manaBonusRepository.getLastTimeBonusClaimed();
    }

    @Override
    public boolean isBonusBitAvailable() {
        return getTimeSinceLastClaim() >= bonusBitTimeout * 60 * 1000;
    }

    @Override
    public int evalCurrentBonus() {
        return bonusBitSize * (int)Math.floor(
                (clamp(getTimeSinceLastClaim(), 0L, minsToMillis(getFullBonusTimeout())) /
                        (double)minsToMillis(getFullBonusTimeout())) * numBonusBits);
    }

    @Override
    public void onBonusClaimed() {
        manaBonusRepository.setLastTimeBonusClaimed(timer.getCurrentTime());
    }

    @Override
    public int getFullBonusTimeout() {
        return bonusBitTimeout * numBonusBits;
    }

}
