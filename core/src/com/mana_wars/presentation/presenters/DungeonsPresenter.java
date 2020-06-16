package com.mana_wars.presentation.presenters;

import com.mana_wars.model.interactor.DungeonsInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.DungeonsView;

public class DungeonsPresenter extends BasePresenter<DungeonsView, DungeonsInteractor> {

    public DungeonsPresenter(DungeonsView view, DungeonsInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void refreshDungeonsList() {
        disposable.add(interactor.getDungeons().subscribe(dungeons -> {

            uiThreadHandler.postRunnable(() -> {
                view.setDungeonsList(dungeons);
            }

            );

        }, Throwable::printStackTrace));
    }

}
