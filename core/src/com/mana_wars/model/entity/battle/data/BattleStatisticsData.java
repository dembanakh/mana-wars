package com.mana_wars.model.entity.battle.data;

public class BattleStatisticsData implements ReadableBattleStatisticsData {

    private int causedDamage;
    private int receivedDamage;
    private int selfHealing;
    private int receivedHealing;

    private BattleStatisticsData(int causedDamage, int receivedDamage, int selfHealing, int receivedHealing) {
        this.causedDamage = causedDamage;
        this.receivedDamage = receivedDamage;
        this.selfHealing = selfHealing;
        this.receivedHealing = receivedHealing;
    }

    public BattleStatisticsData(){
        this(0,0,0,0);
    }

    public void updateValuesAsSource(int selfDelta, int targetDelta) {
        updateWithSelfChange(selfDelta);
        updateWithTargetChange(targetDelta);
    }

    public void updateValuesAsTarget(int delta) {
        if (delta > 0) {
            receivedHealing += delta;
        }
        else {
            receivedDamage -= delta;
        }
    }

    private void updateWithSelfChange(int delta) {
        if (delta > 0) {
            selfHealing += delta;
        } else {
            receivedDamage -= delta;
        }
    }

    private void updateWithTargetChange(int delta){
        if (delta > 0) {
            selfHealing += delta;
        } else {
            causedDamage -= delta;
        }
    }

    @Override
    public int getCausedDamage() {
        return causedDamage;
    }

    @Override
    public int getReceivedDamage() {
        return receivedDamage;
    }

    @Override
    public int getSelfHealing() {
        return selfHealing;
    }

    @Override
    public int getReceivedHealing() {
        return receivedHealing;
    }
}
