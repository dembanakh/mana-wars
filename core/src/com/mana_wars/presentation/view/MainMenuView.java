package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.base.Rarity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.Subject;

public interface MainMenuView {

    void initObservers(CompositeDisposable disposable, Subject<Integer> manaAmountObservable,
                              Subject<Integer> userLevelObservable, Subject<String> usernameObservable);

    void openSkillCaseWindow(int skillID, String skillName, Rarity skillRarity, String description);
    void setTimeSinceLastManaBonusClaimed(long time);
    void onManaBonusReady();

}
