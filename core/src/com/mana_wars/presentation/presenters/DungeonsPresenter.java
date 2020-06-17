package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.model.entity.user.UserDungeonsAPI;
import com.mana_wars.model.interactor.DungeonsInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.DungeonsView;

public final class DungeonsPresenter extends BasePresenter<DungeonsView, DungeonsInteractor> {

    public DungeonsPresenter(DungeonsView view, DungeonsInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void refreshDungeonsList() {
        disposable.add(interactor.getDungeons().subscribe(dungeons -> {

            uiThreadHandler.postRunnable(() -> {
                        view.setDungeonsList(dungeons);
                        for (int i = 0; i < dungeons.size(); ++i) {
                            Dungeon dungeon = dungeons.get(i);
                            view.setDungeonDisabled(i, getUser().getLevel() < dungeon.getRequiredLvl(),
                                    false);
                        }
                    }

            );
        }, Throwable::printStackTrace));
    }

    public void refreshRequiredManaAmount(){

        disposable.add(interactor.getRequiredManaAmountForBattle().subscribe(requiredMana->{

            uiThreadHandler.postRunnable(()->{

                //TODO

            });

        }, Throwable::printStackTrace));

    }

    public UserDungeonsAPI getUser() {
        return interactor.getUser();
    }

}
