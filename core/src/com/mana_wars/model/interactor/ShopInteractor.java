package com.mana_wars.model.interactor;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.entity.user.UserShopAPI;
import com.mana_wars.model.repository.ApplicationDataUpdater;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.model.repository.DailySkillsRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ShopInteractor extends BaseInteractor<UserShopAPI> {

    private final DatabaseRepository databaseRepository;
    private final DailySkillsRepository dailySkillsRepository;

    public ShopInteractor(final UserShopAPI user, final DatabaseRepository databaseRepository,
                          final DailySkillsRepository dailySkillsRepository) {
        super(user);
        this.databaseRepository = databaseRepository;
        this.dailySkillsRepository = dailySkillsRepository;
    }

    public Single<Skill> getNewSkill() {
        return databaseRepository.getSkillsList().map((skills) -> {

            Skill s = SkillFactory.getNewSkill(skills);
            disposable.add(databaseRepository.insertUserSkill(s).subscribe(() -> {
            }, Throwable::printStackTrace));

            return s;
        });
    }

    public void purchaseSkill(ShopSkill shopSkill) {
        if (shopSkill.canBePurchased()) {
            disposable.add(dailySkillsRepository.purchaseSkill(shopSkill)
                    .subscribe(() -> user.updateManaAmount(-shopSkill.getPrice()), Throwable::printStackTrace));
        }
    }

    public int buySkillCases(int actualCases, int costCases) {
        updateManaAmount(- costCases * GameConstants.SKILL_CASE_PRICE);
        return user.updateSkillCases(actualCases);
    }

    public Single<List<ShopSkill>> getPurchasableSkills() {
        return dailySkillsRepository.getDailySkills();
    }

    public int getUserSkillCasesNumber() {
        return user.getSkillCasesNumber();
    }

    public int useSkillCase() {
        return user.updateSkillCases(-1);
    }

    public Observable<Integer> getManaAmountObservable() {
        return user.getManaAmountObservable();
    }

    public Observable<Integer> getUserLevelObservable() {
        return user.getUserLevelObservable();
    }

    public Observable<Integer> getUserExperienceObservable() {
        return user.getExperienceObservable();
    }

    public Observable<Integer> getUserNextLevelRequiredExperienceObservable() {
        return user.getNextLevelRequiredExperienceObservable();
    }

    public void updateManaAmount(int delta) {
        user.updateManaAmount(delta);
    }
}
