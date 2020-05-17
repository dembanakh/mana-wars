package com.mana_wars.model.repository;

import com.mana_wars.model.db.core_entity_converter.SkillConverter;
import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.UserSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DBMapperRepository implements DatabaseRepository {

    private RoomRepository room;

    public DBMapperRepository(RoomRepository room){
        this.room = room;
    }

    @Override
    public Single<List<Skill>> getSkillsList() {
        return room.getSkillsWithCharacteristics().map(dbSkills -> {
            List<Skill> result = new ArrayList<>();
            for(DBSkillWithCharacteristics skill : dbSkills){
                result.add(SkillConverter.toSkill(skill));
            }
            return result;
        });
    }

    @Override
    public Single<List<Skill>> getUserSkills() {
        return room.getCompleteUserSkills().map(completeUserSkills -> {

            List<Skill> result = new ArrayList<>();
            for(CompleteUserSkill skill : completeUserSkills){
                result.add(SkillConverter.toSkill(skill));
            }
            return result;
        });
    }

    @Override
    public Completable insertUserSkill(Skill s) {
        return room.insertEntity(new UserSkill(s.getLevel(), s.getIconID()), room.userSkillsDAO);
    }
}
