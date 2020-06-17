package com.mana_wars.ui;

public final class UIStringConstants {

    public static final String TEXTURE_ATLAS_FORMAT = "%s/%<s.pack";
    public static final String REGION_NAME = "image_part";

    public static final String SKILLS_ICONS_FILENAME = "Skills_icons";
    public static final String SKILLS_FRAMES_FILENAME = "Skills_frames";

    public static class UI_SKIN {
        public static final String FORMAT = "skins/%s/skin/%<s-ui.json";

        public static final String FREEZING = "freezing";
    }

    public static class NAVIGATION_BAR {
        public static final String BG_COLOR = "white";
    }

    public static class GREETING_SCREEN {
        public static final String LABEL_TEXT = "HELLO, USER";
        public static final String BUTTON_TEXT = "START";
    }

    public static class MAIN_MENU_SCREEN {
        public static final String OPEN_SKILL_CASE_BUTTON_TEXT = "OPEN SKILL CASE";
    }

    public static class SKILL_CASE_WINDOW {
        public static final String TITLE = "NEW SKILL";
        public static final String CLOSE_BUTTON_TEXT = "GET";
    }

    public static class SKILL_INFO_WINDOW {
        public static final String TITLE = "SKILL INFO";
        public static final String CLOSE_BUTTON_TEXT = "CLOSE";
    }

    private UIStringConstants() {
    }
}
