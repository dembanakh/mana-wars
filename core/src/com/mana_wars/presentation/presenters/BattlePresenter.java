package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.battle.BattleBuilder;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.BattleRoundsObserver;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.battle.BattleInitializationObserver;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.BattleView;

import java.util.List;

import io.reactivex.functions.Consumer;

public final class BattlePresenter extends BasePresenter<BattleView, BattleInteractor>
        implements BattleInitializationObserver, BattleRoundsObserver {

    public BattlePresenter(BattleView view, BattleInteractor interactor, UIThreadHandler uiThreadHandler) {
        super(view, interactor, uiThreadHandler);
    }

    private void addObserver_userHealth(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserHealthObservable().subscribe(observer));
    }

    private void addObserver_enemyHealth(int index, Consumer<? super Integer> observer) {
        disposable.add(interactor.getEnemyHealthObservable(index).subscribe(observer));
    }

    public void initBattle(BattleBuilder battleBuilder) {
        battleBuilder.setObserver(this);
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
        view.startBattle(interactor.getEnemiesNumber());
    }

    @Override
    public void setSkills(Iterable<ActiveSkill> activeSkills) {
        uiThreadHandler.postRunnable(() -> view.setSkills(activeSkills));
    }

    @Override
    public void setOpponents(BattleParticipant user, Iterable<BattleParticipant> userSide,
                             List<BattleParticipant> enemySide) {
        view.setUser(user.getName(), user.getInitialHealthAmount(), user.getPassiveSkills(),
                this::addObserver_userHealth);

        setEnemies(user, enemySide);
    }

    public void addObserver_userManaAmount(Consumer<? super Integer> userManaAmountObserver) {
        disposable.add(interactor.getUserManaAmountObservable().subscribe(userManaAmountObserver));
    }

    public void changeActiveEnemy() {
        view.setActiveEnemy(interactor.changeUserTarget());
    }

    @Override
    public void setCurrentRound(int round) {
        uiThreadHandler.postRunnable(() -> {
            view.setRound(round + 1);
        });
    }

    @Override
    public void setEnemies(BattleParticipant user, List<BattleParticipant> enemySide) {
        view.cleanEnemies(enemySide.size());
        int index = 0;
        for (BattleParticipant bp : enemySide) {
            int finalIndex = index++;
            view.addEnemy(bp.getName(), bp.getInitialHealthAmount(), bp.getPassiveSkills(),
                    (observer) -> addObserver_enemyHealth(finalIndex, observer));
        }
        uiThreadHandler.postRunnable(() -> {
            view.setActiveEnemy(user.getCurrentTarget());
        });
    }
}
