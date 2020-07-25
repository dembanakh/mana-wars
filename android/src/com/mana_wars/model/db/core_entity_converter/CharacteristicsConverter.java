package com.mana_wars.model.db.core_entity_converter;

import com.mana_wars.model.db.entity.base.DBSkillCharacteristic;
import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.ArrayList;
import java.util.List;

class CharacteristicsConverter {

    static List<SkillCharacteristic> toSkillCharacteristics(
            final List<DBSkillCharacteristic> dbSkillCharacteristics) {
        List<SkillCharacteristic> list = new ArrayList<>();
        for (DBSkillCharacteristic dbsc : dbSkillCharacteristics) {
            list.add(toSkillCharacteristic(dbsc));
        }
        return list;
    }

    static SkillCharacteristic toSkillCharacteristic(final DBSkillCharacteristic dbsc) {
        return new SkillCharacteristic(dbsc.getValue(),
                Characteristic.getCharacteristicById(dbsc.getType()),
                dbsc.getChangeType() ? ValueChangeType.INCREASE :
                        ValueChangeType.DECREASE,
                dbsc.getTarget(),
                UpgradeFunction.valueOf(dbsc.getUpgradeFunction()),
                dbsc.getLevelMultiplier());
    }
}