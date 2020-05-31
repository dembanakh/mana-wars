package com.mana_wars.model.mana_bonus;

import com.mana_wars.model.repository.ManaBonusRepository;

import io.reactivex.functions.Consumer;

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
    public void onBonusClaimed(Consumer<? super Integer> callback) {
        int bonusSize = bonusBitSize * (int)Math.floor(
                (clamp(getTimeSinceLastClaim(), 0L, minsToMillis(getFullBonusTimeout())) /
                        (double)minsToMillis(getFullBonusTimeout())) * numBonusBits);
        manaBonusRepository.setLastTimeBonusClaimed(timer.getCurrentTime());
        try {
            callback.accept(bonusSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getFullBonusTimeout() {
        return bonusBitTimeout * numBonusBits;
    }

    private long minsToMillis(int mins) {
        return mins * 60L * 1000;
    }

    private <T extends Comparable<T>> T clamp(T value, T min, T max) {
        if (value.compareTo(min) < 0) return min;
        if (value.compareTo(max) > 0) return max;
        return value;
    }

}
