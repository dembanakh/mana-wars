package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.user.UserGreetingAPI;
import com.mana_wars.model.interactor.GreetingInteractor;
import com.mana_wars.presentation.presenters.GreetingPresenter;
import com.mana_wars.presentation.view.GreetingView;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;

import static com.mana_wars.ui.UIElementsSize.GREETING_SCREEN.BUTTON_PADDING_BOTTOM;
import static com.mana_wars.ui.UIElementsSize.GREETING_SCREEN.BUTTON_PADDING_TOP;
import static com.mana_wars.ui.UIStringConstants.GREETING_SCREEN;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public final class GreetingScreen extends BaseScreen<BaseOverlayUI, GreetingPresenter> implements GreetingView {

    private final AssetFactory<String, TextureRegion> imageFactory;

    public GreetingScreen(final UserGreetingAPI user,
                          final ScreenSetter screenSetter,
                          final FactoryStorage factoryStorage,
                          final BaseOverlayUI overlayUI) {
        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UI_SKIN.MANA_WARS), overlayUI);

        presenter = new GreetingPresenter(this,
                new GreetingInteractor(user),
                Gdx.app::postRunnable);
        imageFactory = factoryStorage.getImageFactory();
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        layer.add(new Image(imageFactory.getAsset("bg1")));

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        Label label = new Label(GREETING_SCREEN.LABEL_TEXT, skin);
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        label.setFontScale(2);
        layer.add(label).row();
        layer.add(usernameField).padTop(BUTTON_PADDING_TOP).height(64).row();
        layer.add(UIElementFactory.getButton(skin, GREETING_SCREEN.BUTTON_TEXT, new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (usernameField.getText().length() > 0) {
                    presenter.onStart(usernameField.getText());
                } else {
                    // inform user about wrong username
                }
            }
        })).bottom().padTop(BUTTON_PADDING_TOP).padBottom(BUTTON_PADDING_BOTTOM);

        return layer;
    }

    @Override
    public void show() {
        super.show();
        if (!presenter.isFirstTimeAppOpen()) onStart();
    }

    @Override
    public void onStart() {
        setScreen(ScreenInstance.MAIN_MENU, null);
    }
}
