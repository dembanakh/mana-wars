package com.mana_wars.model.interactor;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.entity.user.UserShopAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.Single;
import io.reactivex.subjects.Subject;

public final class ShopInteractor extends BaseInteractor<UserShopAPI> {

    private final DatabaseRepository databaseRepository;

    public ShopInteractor(final UserShopAPI user, final DatabaseRepository databaseRepository) {
        super(user);
        this.databaseRepository = databaseRepository;
    }

    public Subject<Integer> getManaAmountObservable() {
        return user.getManaAmountObservable();
    }

    public Subject<Integer> getUserLevelObservable() {
        return user.getUserLevelObservable();
    }

    public void updateManaAmount(int delta) {
        user.updateManaAmount(delta);
    }

    public Single<Skill> buySkillCase() {
        // obtain cases
        updateManaAmount(- GameConstants.SKILL_CASE_PRICE);
        user.updateSkillCases(1);
        // TODO: change behaviour
        return databaseRepository.getSkillsList().map((skills) -> {
            Skill s = SkillFactory.getNewSkill(skills);
            disposable.add(databaseRepository.insertUserSkill(s).subscribe());
            return s;
        });
    }
    
}
