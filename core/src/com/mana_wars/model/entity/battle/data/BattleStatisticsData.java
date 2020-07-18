package com.mana_wars.model.entity.battle.data;

public class BattleStatisticsData {

    private int causedDamage;
    private int receivedDamage;
    private int selfHealing;
    private int receivedHealing;

    public BattleStatisticsData(int causedDamage, int receivedDamage, int selfHeealing, int receivedHealing) {
        this.causedDamage = causedDamage;
        this.receivedDamage = receivedDamage;
        this.selfHealing = selfHeealing;
        this.receivedHealing = receivedHealing;
    }

    public BattleStatisticsData(){
        this(0,0,0,0);
    }

    public void updateValuesAsSource(int selfDelta, int targetDelta){
        updateWithSelfChange(selfDelta);
        updateWithTargetChange(targetDelta);
    }

    private void updateWithSelfChange(int delta){
        if (delta > 0){
            selfHealing += delta;
        } else {
            receivedDamage -= delta;
        }
    }

    private void updateWithTargetChange(int delta){
        if (delta > 0){
            selfHealing += delta;
        } else {
            causedDamage -= delta;
        }
    }

    public void updateValuesAsTarget(int delta){
        if (delta > 0){
            receivedHealing += delta;
        }
        else {
            receivedDamage -= delta;
        }
    }

    public int getCausedDamage() {
        return causedDamage;
    }

    public int getReceivedDamage() {
        return receivedDamage;
    }

    public int getSelfHealing() {
        return selfHealing;
    }

    public int getReceivedHealing() {
        return receivedHealing;
    }
}
