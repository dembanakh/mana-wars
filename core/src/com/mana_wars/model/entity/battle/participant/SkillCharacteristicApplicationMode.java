package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.CharacteristicsStorage;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

public enum SkillCharacteristicApplicationMode {
    DEFAULT,
    NO_MANA_CONSUMPTION {
        @Override
        public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel, CharacteristicsStorage storage) {
            if (sc.isManaCost()) return;
            super.applySkillCharacteristic(sc, skillLevel, storage);
        }
    };

    public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel,
                                                  CharacteristicsStorage storage) {
        Characteristic c = sc.getCharacteristic();
        int changedValue = c.changeValue(storage.getValue(c), sc.getChangeType(), sc.getValue(skillLevel));
        storage.setValue(c, changedValue);
    }
}
