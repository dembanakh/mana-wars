package com.mana_wars.model.repository;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.db.core_entity_converter.SkillConverter;
import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.UserSkill;
import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                Skill convertedSkill = SkillConverter.toSkill(skill);
                result.add(convertedSkill);
            }
            return result;
        });
    }

    private final HashMap<Skill, UserSkill> lastUserSkillsMap = new HashMap<>();

    @Override
    public Single<Map<SkillTable,List<Skill>>> getUserSkills() {
        return room.getCompleteUserSkills().map(completeUserSkills -> {
            lastUserSkillsMap.clear();

            EnumMap<SkillTable, List<Skill>> result = new EnumMap<>(SkillTable.class);
            for (SkillTable table : SkillTable.values()){
                result.put(table, new ArrayList<>());
            }

            for (int i =0; i < GameConstants.USER_ACTIVE_SKILL_COUNT;i++)
                //TODO refactor
                result.get(SkillTable.ACTIVE_SKILLS).add(ActiveSkill.EmptyActive);
            for (int i =0; i < GameConstants.USER_PASSIVE_SKILL_COUNT;i++)
                result.get(SkillTable.PASSIVE_SKILLS).add(Skill.Empty);

            for(CompleteUserSkill skill : completeUserSkills){

                Skill convertedSkill = SkillConverter.toSkill(skill);
                lastUserSkillsMap.put(convertedSkill, skill.userSkill);

                if (skill.userSkill.getChosen_id()>0){
                    result.get(skill.skill.isActive() ? SkillTable.ACTIVE_SKILLS:
                                                        SkillTable.PASSIVE_SKILLS)
                            .set(skill.userSkill.getChosen_id()-1, convertedSkill);
                }
                else {
                    result.get(SkillTable.ALL_SKILLS).add(convertedSkill);
                }
            }
            return result;
        });
    }

    @Override
    public Completable mergeSkills(Skill toUpdate, Skill toDelete) {
        UserSkill userSkill = lastUserSkillsMap.get(toUpdate);
        userSkill.setLvl(toUpdate.getLevel());
        return room.mergeSkills(userSkill, lastUserSkillsMap.get(toDelete));
    }

    @Override
    public Completable moveSkill(Skill toUpdate, int index) {
        UserSkill userSkill = lastUserSkillsMap.get(toUpdate);
        userSkill.setChosen_id(index+1);

        return room.updateEntity(userSkill, room.userSkillsDAO);
    }

    @Override
    public Completable swapSkills(Skill skillSource, Skill skillTarget) {
        UserSkill source = lastUserSkillsMap.get(skillSource);
        UserSkill target = lastUserSkillsMap.get(skillTarget);

        int swap = source.getChosen_id();
        source.setChosen_id(target.getChosen_id());
        target.setChosen_id(swap);

        return room.updateEntities(Arrays.asList(source, target), room.userSkillsDAO);
    }

    @Override
    public Completable insertUserSkill(Skill s) {
        return room.insertEntity(new UserSkill(s.getLevel(), s.getIconID()), room.userSkillsDAO);
    }
}
