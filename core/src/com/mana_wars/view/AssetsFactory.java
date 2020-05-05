package com.mana_wars.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;

public final class AssetsFactory {

    public static Skin getSkin(String name) {
        return skins.get(name);
    }

    private static HashMap<String, Skin> skins = new HashMap<>();

    static {
        loadSkin("freezing");
    }

    // TODO make more flexible
    private static void loadSkin(String name) {
        String path = String.format("skins/%s/skin/%s-ui.json", name, name);
        skins.put(name, new Skin(Gdx.files.internal(path)));
    }

}
