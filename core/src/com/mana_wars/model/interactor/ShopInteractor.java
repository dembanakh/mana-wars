package com.mana_wars.model.interactor;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.entity.user.UserShopAPI;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.model.repository.DailySkillsRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public final class ShopInteractor extends BaseInteractor<UserShopAPI> {

    private final DatabaseRepository databaseRepository;
    private final DailySkillsRepository dailySkillsRepository;
    private final List<ShopSkill> shopSkills = new ArrayList<>();

    public ShopInteractor(final UserShopAPI user, final DatabaseRepository databaseRepository,
                          final DailySkillsRepository dailySkillsRepository) {
        super(user);
        this.databaseRepository = databaseRepository;
        this.dailySkillsRepository = dailySkillsRepository;

    }


    //TODO refactor
    public Single<Skill> getNewSkill() {
        return databaseRepository.getSkillsList().map((skills) -> {

            Skill s = SkillFactory.getNewSkill(skills);
            disposable.add(databaseRepository.insertUserSkill(s).subscribe(() -> {
            }, Throwable::printStackTrace));

            return s;
        });
    }

    public Single<List<ShopSkill>> getPurchasableSkills() {
        return dailySkillsRepository.getDailySkills();
    }

    public void purchaseSkill(ShopSkill shopSkill) {
        if (!shopSkill.canBePurchased()) return;
        user.updateManaAmount(-shopSkill.getPrice());
        dailySkillsRepository.buySkill(shopSkill.purchaseSkill());
        //disposable.add(databaseRepository.insertUserSkill(shopSkill.purchaseSkill()).subscribe(() -> {}, Throwable::printStackTrace));
    }

    public int buySkillCase() {
        // obtain cases
        updateManaAmount(- GameConstants.SKILL_CASE_PRICE);
        return user.updateSkillCases(1);
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
