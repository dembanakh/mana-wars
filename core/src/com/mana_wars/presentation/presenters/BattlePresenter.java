package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.battle.BaseBattleBuilder;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.interactor.BattleInitializationObserver;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.BattleView;

import io.reactivex.functions.Consumer;

public final class BattlePresenter extends BasePresenter<BattleView, BattleInteractor> implements BattleInitializationObserver {

    public BattlePresenter(BattleView view, BattleInteractor interactor, UIThreadHandler uiThreadHandler) {
        super(view, interactor, uiThreadHandler);
    }

    private void addObserver_userHealth(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserHealthObservable().subscribe(observer));
    }

    private void addObserver_enemyHealth(int index, Consumer<? super Integer> observer) {
        disposable.add(interactor.getEnemyHealthObservable(index).subscribe(observer));
    }

    public void initBattle(BaseBattleBuilder battleBuilder) {
        interactor.init(this, battleBuilder);
    }

    public void applyUserSkill(int appliedSkillIndex) {
        if (interactor.tryApplyUserSkill(appliedSkillIndex)) {
            view.blockSkills(appliedSkillIndex);
        }
    }

    public void updateBattle(float timeDelta) {
        interactor.updateBattle(timeDelta);
    }

    @Override
    public void onStartBattle() {
        disposable.add(interactor.getFinishBattleObservable().subscribe(
                (data) -> uiThreadHandler.postRunnable(() -> view.finishBattle(data))
        ));
        view.startBattle();
    }

    @Override
    public void setSkills(Iterable<ActiveSkill> activeSkills) {
        uiThreadHandler.postRunnable(() -> view.setSkills(activeSkills));
    }

    @Override
    public void setOpponents(BattleParticipant user, Iterable<BattleParticipant> userSide,
                             Iterable<BattleParticipant> enemySide) {
        view.setUser(user.getName(), user.getInitialHealthAmount(), user.getPassiveSkills(),
                this::addObserver_userHealth);
        int index = 0;
        for (BattleParticipant bp : enemySide) {
            int finalIndex = index++;
            view.addEnemy(bp.getName(), bp.getInitialHealthAmount(), bp.getPassiveSkills(),
                    (observer) -> addObserver_enemyHealth(finalIndex, observer));
        }
        uiThreadHandler.postRunnable(() -> {
            view.setActiveEnemy(0);
        });
    }

    public void addObserver_userManaAmount(Consumer<? super Integer> userManaAmountObserver) {
        disposable.add(interactor.getUserManaAmountObservable().subscribe(userManaAmountObserver));
    }
}
