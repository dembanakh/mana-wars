package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.battle.BattleStateObserverStarter;
import com.mana_wars.model.entity.battle.builder.BattleBuilder;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.BattleView;

import java.util.List;

import io.reactivex.functions.Consumer;

public final class BattlePresenter extends BasePresenter<BattleView, BattleInteractor>
        implements BattleStateObserverStarter {

    public BattlePresenter(BattleView view, BattleInteractor interactor, UIThreadHandler uiThreadHandler) {
        super(view, interactor, uiThreadHandler);
    }

    public void initBattle(BattleBuilder battleBuilder) {
        interactor.init(this, battleBuilder);
    }

    public void applyUserSkill(ActiveSkill skill, int appliedSkillIndex) {
        if (interactor.tryApplyUserSkill(skill)) {
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
        disposable.add(user.getHealthObservable().subscribe(view.setUser(user.getData())));

        setEnemies(enemySide, user.getCurrentTarget());
    }

    public void changeActiveEnemy() {
        view.setActiveEnemy(interactor.changeUserTarget());
    }

    @Override
    public void setCurrentRound(int round) {
        uiThreadHandler.postRunnable(() -> view.setRound(round));
    }

    @Override
    public void setEnemies(List<BattleParticipant> enemySide, int userTarget) {
        view.cleanEnemies();
        for (BattleParticipant enemy : enemySide) {
            disposable.add(enemy.getHealthObservable()
                    .subscribe(view.addEnemy(enemy.getData())));
        }
        view.setEnemyCount(enemySide.size());
        uiThreadHandler.postRunnable(() -> view.setActiveEnemy(userTarget));
    }

    @Override
    public void updateDurationCoefficients(int castTime, int cooldown) {
        view.updateDurationCoefficients(castTime, cooldown);
    }

    public void addObserver_userManaAmount(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserManaAmountObservable().subscribe(observer));
    }
}
