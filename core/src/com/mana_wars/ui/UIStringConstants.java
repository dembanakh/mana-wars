package com.mana_wars.ui;

public final class UIStringConstants {

    public static final String TEXTURE_ATLAS_FORMAT = "%s/%<s.pack";
    public static final String REGION_NAME = "image_part";

    public static final String SKILLS_ICONS_FILENAME = "Skills_icons";
    public static final String SKILLS_FRAMES_FILENAME = "Skills_frames";

    public static class UI_SKIN {
        public static final String FORMAT = "skins/%s/%<s-ui.json";

        public static final String FREEZING = "freezing";
        public static final String MANA_WARS = "mana-wars";

        public enum BACKGROUND_COLOR {
            NONE, WHITE, BROWN;

            @Override
            public String toString() {
                return super.toString().toLowerCase();
            }
        }
    }

    public static class LOADING_SCREEN {
        public static final String LOADING_KEY = "Loading";
    }

    public static class GREETING_SCREEN {
        public static final String LABEL_KEY = "HelloUser";
        public static final String INPUT_FIELD_KEY = "Username";
        public static final String BUTTON_KEY = "Start";
    }

    public static class MAIN_MENU_SCREEN {
        public static final String OPEN_SKILL_CASE_BUTTON_KEY = "OpenSkillCase";
    }

    public static class SHOP_SCREEN {
        public static final String ONE_SKILL_CASE_KEY = "1SkillCase";
    }

    public static class SKILLS_INFO_SCREEN {
        public static final String TO_MAIN_MENU_KEY = "ToMainMenu";
    }

    public static class BATTLE_SCREEN {
        public static final String ROUND_KEY = "Round";
        public static final String CHANGE_ENEMY_KEY = "ChangeEnemy";
        public static final String LEAVE_KEY = "Leave";
    }

    public static class BATTLE_SUMMARY_SCREEN {
        public static final String BATTLE_FINISHED_KEY = "BattleFinished";
        public static final String TO_MAIN_MENU_KEY = "ToMainMenu";
        public static final String MANA_REWARD_KEY = "ManaReward";
        public static final String XP_REWARD_KEY = "XpReward";
        public static final String CASES_REWARD_KEY = "CasesReward";
    }

    public static class NAVIGATION_BAR {
        public static final String BG_COLOR = "brown";
        public static final String MAIN_KEY = "Main";
        public static final String SKILLS_KEY = "Skills";
        public static final String DUNGEONS_KEY = "Dungeons";
        public static final String INFO_KEY = "Info";
        public static final String SHOP_KEY = "Shop";
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
