package com.mana_wars.model.db.core_entity_converter;

import com.mana_wars.model.db.entity.DBSkillCharacteristic;
import com.mana_wars.model.entity.base.UpgradeFunction;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.ArrayList;
import java.util.List;

class CharacteristicsConverter {

    static List<SkillCharacteristic> toSkillCharacteristics(
            final List<DBSkillCharacteristic> dbSkillCharacteristics) {

        List<SkillCharacteristic> list = new ArrayList<>();
        for (DBSkillCharacteristic dbsc : dbSkillCharacteristics) {
            SkillCharacteristic skillCharacteristic = new SkillCharacteristic(dbsc.getValue(),
                    Characteristic.getCharacteristicById(dbsc.getType()),
                    dbsc.getChangeType() ? ValueChangeType.INCREASE :
                            ValueChangeType.DECREASE,
                    dbsc.getTarget() == 1 ? SkillCharacteristic.Target.SELF :
                            SkillCharacteristic.Target.ENEMY,
                    UpgradeFunction.valueOf(dbsc.getUpgradeFunction()),
                    dbsc.getLevelMultiplier());
            list.add(skillCharacteristic);
        }
        return list;
    }

}