package com.mana_wars.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.NoSuchElementException;

public final class AssetsFactory {

    public static Skin getSkin(String name) {
        return skins.get(name);
    }

    public static TextureRegion getSkillIcon(int index) {
        final String name = "image_part";
        TextureAtlas.AtlasRegion result = skillsIcons.get(name);
        if (result == null) {
            result = skillsIconsAtlas.findRegion(name, index);
            if (result == null)
                throw new NoSuchElementException(
                        String.format("There is no textureRegion with such (name, index): (%s, %d)", name, index));
            skillsIcons.put(index, result);
        }

        return result;
    }

    private static final HashMap<String, Skin> skins = new HashMap<>();

    private static final TextureAtlas skillsIconsAtlas;
    private static final HashMap<Integer, TextureAtlas.AtlasRegion> skillsIcons = new HashMap<>();

    static {
        loadSkin("freezing");
        skillsIconsAtlas = loadAtlas("Skills_icons");
    }

    // TODO make more flexible
    private static void loadSkin(String name) {
        String path = String.format("skins/%s/skin/%s-ui.json", name, name);
        skins.put(name, new Skin(Gdx.files.internal(path)));
    }

    private static TextureAtlas loadAtlas(String name) {
        return new TextureAtlas(String.format("%s/%s.pack", name, name));
    }

}
