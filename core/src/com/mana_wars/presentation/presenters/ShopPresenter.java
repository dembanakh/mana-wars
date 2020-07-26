package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.model.interactor.ShopInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.ShopView;

import java.util.Collections;

import io.reactivex.functions.Consumer;

public final class ShopPresenter extends BasePresenter<ShopView, ShopInteractor> {

    public ShopPresenter(ShopView view, ShopInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void buyOneSkillCase() {
        view.setSkillCasesNumber(interactor.buySkillCases(1, 1));
    }

    public void buyTenSkillCases() {
        view.setSkillCasesNumber(interactor.buySkillCases(10, 9));
    }

    public void refreshPurchasableSkills() {
        disposable.add(interactor.getPurchasableSkills().subscribe(
                shopSkills -> {
                    uiThreadHandler.postRunnable(
                            () -> view.setPurchasableSkills(shopSkills)
                    );
                },
                e -> {
                    e.printStackTrace();
                    uiThreadHandler.postRunnable(
                            () -> view.setPurchasableSkills(Collections.emptyList())
                    );
                }
        ));
    }

    public void onOpenSkillCase() {
        if (interactor.getUserSkillCasesNumber() > 0) {
            disposable.add(interactor.getNewSkill().subscribe(s -> {
                final int actualCasesNumber = interactor.useSkillCase();
                uiThreadHandler.postRunnable(() -> {
                    view.openSkillCaseWindow(s);
                    view.setSkillCasesNumber(actualCasesNumber);
                });
            }, Throwable::printStackTrace));
        }
    }

    public void refreshSkillCasesNumber() {
        view.setSkillCasesNumber(interactor.getUserSkillCasesNumber());
    }

    public void purchaseSkill(ShopSkill skill) {
        interactor.purchaseSkill(skill);
    }

    public void addObserver_manaAmount(Consumer<? super Integer> observer) {
        disposable.add(interactor.getManaAmountObservable().subscribe(observer));
    }

    public void addObserver_userLevel(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserLevelObservable().subscribe(observer));
    }

    public void addObserver_userExperience(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserExperienceObservable().subscribe(observer));
    }

    public void addObserver_userNextLevelRequiredExperienceObserver(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserNextLevelRequiredExperienceObservable().subscribe(observer));
    }
}
