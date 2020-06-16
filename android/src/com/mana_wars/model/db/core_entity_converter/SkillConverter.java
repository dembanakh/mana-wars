package com.mana_wars.model.db.core_entity_converter;

import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.entity.skills.Skill;

//TODO think about better impl
public class SkillConverter {

    public static Skill toSkill(DBSkillWithCharacteristics skill){
        return skill.skill.isActive()? toActiveSkill(skill, 1): toPassiveSkill(skill, 1);
    }

    public static ActiveSkill toActiveSkill(DBSkillWithCharacteristics skill, int lvl){
        return new ActiveSkill(
                skill.skill.getId(),
                lvl,
                Rarity.getRarityByID(skill.skill.getRarity()),
                skill.skill.getCastTime(),
                skill.skill.getCooldown(),
                skill.skill.getName(),
                CharacteristicsConverter.toSkillCharacteristics(skill.characteristics)
        );
    }

    public static PassiveSkill toPassiveSkill(DBSkillWithCharacteristics skill, int lvl) {
        return new PassiveSkill(
                skill.skill.getId(),
                lvl,
                Rarity.getRarityByID(skill.skill.getRarity()),
                skill.skill.getName(),
                CharacteristicsConverter.toSkillCharacteristics(skill.characteristics)
        );
    }

    public static Skill toSkill(CompleteUserSkill userSkill){
        return userSkill.skill.isActive()?toActiveSkill(userSkill):toPassiveSkill(userSkill);
    }

    public static ActiveSkill toActiveSkill(CompleteUserSkill userSkill){
        return new ActiveSkill(
                userSkill.skill.getId(),
                userSkill.userSkill.getLvl(),
                Rarity.getRarityByID(userSkill.skill.getRarity()),
                userSkill.skill.getCastTime(),
                userSkill.skill.getCooldown(),
                userSkill.skill.getName(),
                CharacteristicsConverter.toSkillCharacteristics(userSkill.characteristics)
                );
    }

    public static PassiveSkill toPassiveSkill(CompleteUserSkill userSkill) {
        return new PassiveSkill(
                userSkill.skill.getId(),
                userSkill.userSkill.getLvl(),
                Rarity.getRarityByID(userSkill.skill.getRarity()),
                userSkill.skill.getName(),
                CharacteristicsConverter.toSkillCharacteristics(userSkill.characteristics)
        );
    }
}
