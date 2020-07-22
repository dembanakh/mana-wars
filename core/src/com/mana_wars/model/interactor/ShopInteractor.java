package com.mana_wars.model.interactor;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.entity.user.UserShopAPI;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.entity.skills.ShopSkill;
import com.mana_wars.model.repository.ShopRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public final class ShopInteractor extends BaseInteractor<UserShopAPI> {

    private final DatabaseRepository databaseRepository;
    private final ShopRepository shopRepository;
    private final List<ShopSkill> shopSkills = new ArrayList<>();

    public ShopInteractor(final UserShopAPI user, final DatabaseRepository databaseRepository,
                          final ShopRepository shopRepository) {
        super(user);
        this.databaseRepository = databaseRepository;
        this.shopRepository = shopRepository;
        //TODO Temp
        disposable.add(databaseRepository.getSkillsList()
                .repeat(6)
                .map(SkillFactory::getNewSkill)
                .map(ShopSkill::new)
                .subscribe(shopSkills::add));
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

    public int buySkillCase() {
        // obtain cases
        updateManaAmount(- GameConstants.SKILL_CASE_PRICE);
        return user.updateSkillCases(1);
    }

    public int getUserSkillCasesNumber() {
        return user.getSkillCasesNumber();
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

    public int useSkillCase() {
        return user.updateSkillCases(-1);
    }

/*
    public void refreshPurchasableSkills() {
        //temp
        if (true) return;

        Calendar nextRefresh = shopRepository.getLastRefreshTime();
        if (nextRefresh == null) {
            Calendar currentTime = GregorianCalendar.getInstance();
            refreshPurchasableSkills(new GregorianCalendar(
                    currentTime.get(Calendar.YEAR),
                    currentTime.get(Calendar.MONTH),
                    currentTime.get(Calendar.DATE)));
        }
        else {
            nextRefresh.add(Calendar.HOUR, 24);
            if (nextRefresh.before(GregorianCalendar.getInstance()))
                refreshPurchasableSkills(nextRefresh);
        }
    }

    private void refreshPurchasableSkills(Calendar nextRefresh) {
        shopRepository.updateRefreshTime(nextRefresh);
        shopSkills.clear();
    }
*/

    public Iterable<? extends ShopSkill> getPurchasableSkills() {
        return shopSkills;
    }

    public void purchaseSkill(ShopSkill shopSkill) {
        if (shopSkill.isPurchased()) return;

        user.updateManaAmount(-shopSkill.getPrice());
        disposable.add(databaseRepository.insertUserSkill(shopSkill.purchaseSkill()).subscribe(() -> {}, Throwable::printStackTrace));
    }
}
