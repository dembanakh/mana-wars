package com.mana_wars.model.repository;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.SkillsListTriple;
import com.mana_wars.model.db.core_entity_converter.SkillConverter;
import com.mana_wars.model.db.entity.CompleteUserSkill;
import com.mana_wars.model.db.entity.DBDungeon;
import com.mana_wars.model.db.entity.DBMobSkillWithCharacteristics;
import com.mana_wars.model.db.entity.DBMobWithSkills;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.UserSkill;
import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.model.entity.enemy.Mob;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class DBMapperRepository implements DatabaseRepository {

    private final RoomRepository room;

    public DBMapperRepository(RoomRepository room) {
        this.room = room;
    }

    @Override
    public Single<List<Skill>> getSkillsList() {
        return room.getSkillsWithCharacteristics().map(dbSkills -> {
            List<Skill> result = new ArrayList<>();
            for (DBSkillWithCharacteristics skill : dbSkills) {
                Skill convertedSkill = SkillConverter.toSkill(skill);
                result.add(convertedSkill);
            }
            return result;
        });
    }

    private final Map<Skill, UserSkill> lastUserSkillsMap = new HashMap<>();

    @Override
    public Single<SkillsListTriple> getUserSkills() {
        return room.getCompleteUserSkills().map(completeUserSkills -> {
            lastUserSkillsMap.clear();

            SkillsListTriple result = new SkillsListTriple();

            for (int i = 0; i < GameConstants.MAX_CHOSEN_ACTIVE_SKILL_COUNT; i++)
                //TODO refactor
                result.activeSkills.add(ActiveSkill.getEmpty());
            for (int i = 0; i < GameConstants.USER_PASSIVE_SKILL_COUNT; i++)
                result.passiveSkills.add(PassiveSkill.getEmpty());

            for (CompleteUserSkill skill : completeUserSkills) {

                Skill convertedSkill;

                if (skill.userSkill.getChosen_id() > 0) {
                    if (skill.skill.isActive()) {
                        ActiveSkill converted = SkillConverter.toActiveSkill(skill);
                        result.activeSkills.set(skill.userSkill.getChosen_id() - 1, converted);
                        convertedSkill = converted;
                    } else {
                        PassiveSkill converted = SkillConverter.toPassiveSkill(skill);
                        result.passiveSkills.set(skill.userSkill.getChosen_id() - 1, converted);
                        convertedSkill = converted;
                    }
                } else {
                    result.allSkills.add(convertedSkill = SkillConverter.toSkill(skill));
                }
                lastUserSkillsMap.put(convertedSkill, skill.userSkill);
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
        userSkill.setChosen_id(index + 1);

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


    private final Map<Dungeon, DBDungeon> lastDungeonsMap = new HashMap<>();

    //TODO think about this method
    @Override
    public Single<List<Dungeon>> getDungeons() {
        return room.getAllEntities(room.dbDungeonDAO).map(dbDungeons -> {
            lastDungeonsMap.clear();
            List<Dungeon> dungeons = new ArrayList<>();
            for (DBDungeon dbDungeon : dbDungeons) {
                Dungeon dungeon = new Dungeon(dbDungeon.getId(), dbDungeon.getName(), dbDungeon.getRequiredLvl());
                lastDungeonsMap.put(dungeon, dbDungeon);
                dungeons.add(dungeon);
            }
            return dungeons;
        });
    }

    @Override
    public Single<List<Mob>> getMobsListByDungeon(Dungeon dungeon) {
        return room.getDBMobsWithSkillsByDungeonID(lastDungeonsMap.get(dungeon).getId()).map(
                dbMobsWithSkills -> {

                    List<Mob> mobs = new ArrayList<>();
                    for (DBMobWithSkills mob : dbMobsWithSkills) {

                        List<ActiveSkill> activeSkills = new ArrayList<>();
                        List<PassiveSkill> passiveSkills = new ArrayList<>();

                        for (DBMobSkillWithCharacteristics skill : mob.skills) {

                            if (skill.skill.skill.isActive()) {
                                activeSkills.add(SkillConverter.toActiveSkill(skill.skill, skill.dbMobSkill.getLvl()));
                            } else {
                                passiveSkills.add(SkillConverter.toPassiveSkill(skill.skill, skill.dbMobSkill.getLvl()));
                            }

                        }

                        mobs.add(new Mob(mob.mob.getName(), mob.mob.getInitialHealth(), activeSkills, passiveSkills));
                    }
                    return mobs;
                });
    }

    @Override
    public Single<Integer> getRequiredManaAmountForBattle() {
        return room.getChosenPassiveSkills().map(userChosenPassiveSkills -> {
            int result = 0;
            for (CompleteUserSkill skill : userChosenPassiveSkills) {
                result += SkillConverter.toSkill(skill).getManaCost();
            }
            return result;
        });
    }

    @Override
    public Completable insertUserSkill(Skill s) {
        return room.insertEntity(new UserSkill(s.getLevel(), s.getIconID()), room.userSkillsDAO);
    }


}
