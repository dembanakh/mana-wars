package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.CharacteristicsStorage;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

public abstract class SkillCharacteristicApplicator {

    protected CharacteristicsStorage storage;

    void setStorage(CharacteristicsStorage storage) {
        this.storage = storage;
    }

    public abstract void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel);
}
