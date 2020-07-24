package com.mana_wars.model.repository;

import com.mana_wars.model.db.core_entity_converter.SkillConverter;
import com.mana_wars.model.db.entity.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.UserSkill;
import com.mana_wars.model.entity.ShopSkill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DailySkillsRepositoryImpl implements DailySkillsRepository {

    private final RoomRepository repository;
    private final SharedPreferencesRepository preferences;
    private final Map<ShopSkill, Integer> lastFetchSkillsMap = new HashMap<>();

    public DailySkillsRepositoryImpl(RoomRepository repository, SharedPreferencesRepository preferences){
        this.repository = repository;
        this.preferences = preferences;
    }

    private Map<Integer, Integer> getDailySkillsIDsMap(){
        Map<Integer, Integer> result = new HashMap<>();
        //TODO add max size
        for (int i = 0; i < 3; i++) {
            result.put(preferences.getDailySkillID(i),i);
        }
        return result;
    }

    @Override
    public Single<Iterable<ShopSkill>> getDailySkills() {
        Map<Integer, Integer> idsMap = getDailySkillsIDsMap();
        return repository.getSkillsWithCharacteristicsByIDs(new ArrayList<>(getDailySkillsIDsMap().keySet()))
                .map(dbSkills -> {
                    List<ShopSkill> result = new ArrayList<>();
                    lastFetchSkillsMap.clear();
                    for (DBSkillWithCharacteristics skill : dbSkills) {
                        int id = idsMap.get(skill.skill.getId());
                        ShopSkill shopSkill = new ShopSkill(
                                SkillConverter.toSkill(skill),
                                preferences.getDailySkillBought(id),
                                preferences.getDailySkillPrice(id));
                        lastFetchSkillsMap.put(shopSkill, id);
                        result.add(shopSkill);
                    }
                    return result;
                });
    }

    @Override
    public Completable purchaseSkill(ShopSkill skill) {
        int id = lastFetchSkillsMap.get(skill);
        UserSkill userSkill = new UserSkill(skill.getLevel(), skill.getIconID());
        preferences.setDailySkillBought(id, skill.purchaseSkill());
        return repository.insertEntity(userSkill, repository.userSkillsDAO);
    }
}
