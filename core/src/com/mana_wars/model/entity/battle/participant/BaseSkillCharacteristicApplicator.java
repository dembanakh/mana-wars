package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

class BaseSkillCharacteristicApplicator extends SkillCharacteristicApplicator {
    @Override
    public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel) {
        Characteristic c = sc.getCharacteristic();
        int changedValue = c.changeValue(storage.getValue(c), sc.getChangeType(), sc.getValue(skillLevel));
        storage.setValue(c, changedValue);
    }
}
