package com.mana_wars.ui.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class LocalizedStringFactory {

    private final I18NBundle bundle;

    public LocalizedStringFactory(String bundlePath, Locale locale) {
        bundle = I18NBundle.createBundle(Gdx.files.internal(bundlePath), locale);
    }

    public LocalizedStringFactory(String bundlePath) {
        this(bundlePath, Locale.getDefault());
    }

    public String get(String key) {
        return bundle.get(key);
    }

    public String format(String key, Object... args) {
        return bundle.format(key, args);
    }

}
