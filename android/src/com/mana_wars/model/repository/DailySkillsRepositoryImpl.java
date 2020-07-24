package com.mana_wars.model.repository;

import com.mana_wars.model.db.core_entity_converter.SkillConverter;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.UserSkill;
import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DailySkillsRepositoryImpl implements DailySkillsRepository {

    private final RoomRepository repository;
    private final SharedPreferencesRepository preferences;
    private final Map<Skill, Integer> lastFetchSkillsMap = new HashMap<>();

    public DailySkillsRepositoryImpl(RoomRepository repository, SharedPreferencesRepository preferences){
        this.repository = repository;
        this.preferences = preferences;
    }

    private Map<Integer, Integer> getDailySkillsIDsMap(){
        Map<Integer, Integer> result = new HashMap<>();
        //TODO add max size
        for(int i=0; i < 3; i++){
            result.put(preferences.getDailySkillID(i),i);
        }
        return result;
    }

    @Override
    public Single<List<ShopSkill>> getDailySkills() {
        Map<Integer, Integer> idsMap = getDailySkillsIDsMap();
        return repository.getSkillsWithCharacteristicsByIDs(new ArrayList<>(getDailySkillsIDsMap().keySet()))
                .map(dbSkills -> {
                    List<ShopSkill> result = new ArrayList<>();
                    lastFetchSkillsMap.clear();
                    for(DBSkillWithCharacteristics skill : dbSkills){
                        int id = idsMap.get(skill.skill.getId());
                        Skill s = SkillConverter.toSkill(skill);
                        lastFetchSkillsMap.put(s, id);
                        result.add(new ShopSkill(
                                                 s,
                                                 preferences.getDailySkillBought(id),
                                                 preferences.getDailySkillPrice(id))
                        );
                    }
                    return result;
                });
    }

    @Override
    public Completable buySkill(Skill s) {
        int id = lastFetchSkillsMap.get(s);
        preferences.setDailySkillBought(id, preferences.getDailySkillBought(id) + 1);
        return repository.insertEntity(new UserSkill(s.getLevel(), s.getIconID()), repository.userSkillsDAO);
    }
}
