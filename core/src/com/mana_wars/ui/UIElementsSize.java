package com.mana_wars.ui;

import com.badlogic.gdx.Gdx;

public final class UIElementsSize {

    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public static class SKILL_CASE_WINDOW {
        public static final int SKILL_NAME_PADDING = 10;
        public static final int SKILL_ICON_PADDING = 50;
        public static final int SKILL_DESCRIPTION_PADDING = 50;

        public static final int GET_BUTTON_PADDING_TOP    = 50;
        public static final int GET_BUTTON_PADDING_LEFT   = 100;
        public static final int GET_BUTTON_PADDING_BOTTOM = 25;
        public static final int GET_BUTTON_PADDING_RIGHT  = 100;
    }

    public static class GREETING_SCREEN {
        public static final int BUTTON_PADDING_TOP    = 100;
        public static final int BUTTON_PADDING_BOTTOM = 100;
    }

    public static class SKILLS_SCREEN {
        public static final int COLUMNS_NUMBER = 5;
        public static final float  ACTIVE_SKILLS_TABLE_HEIGHT = SCREEN_HEIGHT * 0.1f;
        public static final float PASSIVE_SKILLS_TABLE_HEIGHT = SCREEN_HEIGHT * 0.1f;
        public static final float    MAIN_SKILLS_TABLE_HEIGHT = SCREEN_HEIGHT * 0.7f;
        public static final float SKILLS_TABLES_WIDTH = SCREEN_WIDTH;
    }

    public static class NAVIGATION_BAR {
        public static final int TABS_NUMBER = 4;
        public static final float TAB_HEIGHT = SCREEN_HEIGHT * 0.1f;
        public static final float TAB_WIDTH  = (float) SCREEN_WIDTH / TABS_NUMBER;
    }


    private UIElementsSize() {}
}
