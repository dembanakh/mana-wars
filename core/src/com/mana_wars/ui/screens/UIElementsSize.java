package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;

final class UIElementsSize {

    static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    static class NAVIGATION_BAR {
        static final int TABS_NUMBER = 4;
        static final float TAB_HEIGHT = SCREEN_HEIGHT * 0.1f;
        static final float TAB_WIDTH  = (float) SCREEN_WIDTH / TABS_NUMBER;
    }

    static class SKILL_CASE_WINDOW {
        static final int SKILL_NAME_PADDING = 10;
        static final int SKILL_ICON_PADDING = 50;
        static final int SKILL_DESCRIPTION_PADDING = 50;

        static final int GET_BUTTON_PADDING_TOP    = 50;
        static final int GET_BUTTON_PADDING_LEFT   = 100;
        static final int GET_BUTTON_PADDING_BOTTOM = 25;
        static final int GET_BUTTON_PADDING_RIGHT  = 100;
    }

    static class GREETING_SCREEN {
        static final int BUTTON_PADDING_TOP    = 100;
        static final int BUTTON_PADDING_BOTTOM = 100;
    }

    static class SKILLS_SCREEN {
        static final int COLUMNS_NUMBER = 5;
        static final float  ACTIVE_SKILLS_TABLE_HEIGHT = SCREEN_HEIGHT * 0.1f;
        static final float PASSIVE_SKILLS_TABLE_HEIGHT = SCREEN_HEIGHT * 0.1f;
        static final float    MAIN_SKILLS_TABLE_HEIGHT = SCREEN_HEIGHT * 0.7f;
        static final float SKILLS_TABLES_WIDTH = SCREEN_WIDTH;
    }


    private UIElementsSize() {}
}
