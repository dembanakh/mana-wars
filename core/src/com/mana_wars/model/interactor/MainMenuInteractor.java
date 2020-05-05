package com.mana_wars.model.interactor;

import com.mana_wars.model.repository.LocalUserDataRepository;

public class MainMenuInteractor {

    LocalUserDataRepository localUserDataRepository;

    public MainMenuInteractor(LocalUserDataRepository ludr){
        this.localUserDataRepository = ludr;
    }
}
