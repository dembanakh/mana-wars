package com.mana_wars.model.repository;

import com.mana_wars.model.db.core_entity_converter.SkillConverter;
import com.mana_wars.model.db.entity.query.DBSkillWithCharacteristics;
import com.mana_wars.model.db.entity.query.UserSkill;
import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.utils.UpdateChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mana_wars.model.GameConstants.DAILY_SKILLS_COUNT;

public class DailySkillsRepositoryImpl implements DailySkillsRepository {

    private final RoomRepository repository;
    private final SharedPreferencesRepository preferences;
    private final UpdateChecker checker;
    private final Map<ShopSkill, Integer> lastFetchSkillsMap = new HashMap<>();


    public DailySkillsRepositoryImpl(RoomRepository repository, SharedPreferencesRepository preferences, UpdateChecker checker) {
        this.repository = repository;
        this.preferences = preferences;
        this.checker = checker;
    }

    private Map<Integer, Integer> getDailySkillsIDsMap() {
        Map<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < DAILY_SKILLS_COUNT; i++) {
            int id = preferences.getDailySkillID(i);
            if(id > -1) result.put(id, i);
        }
        return result;
    }

    @Override
    public Single<List<ShopSkill>> getDailySkills() {
        return checker.checkDailySkillsOffer().andThen(repository.getSkillsWithCharacteristicsByIDs(new ArrayList<>(getDailySkillsIDsMap().keySet()))).map(
                dbSkills -> {
                    List<ShopSkill> result = new ArrayList<>();
                    lastFetchSkillsMap.clear();
                    Map<Integer, Integer> idsMap = getDailySkillsIDsMap();
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
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Completable purchaseSkill(ShopSkill skill) {
        int id = lastFetchSkillsMap.get(skill);
        UserSkill userSkill = new UserSkill(skill.getLevel(), skill.getIconID());
        preferences.setDailySkillBought(id, skill.purchaseSkill());
        return repository.insertEntity(userSkill, repository.userSkillsDAO);
    }
}
