package com.mana_wars.model;

public final class GameConstants {

    public static final int DEFAULT_USER_INITIAL_HEALTH = 300;

    public static final int MAX_CHOSEN_ACTIVE_SKILL_COUNT = 5;
    public static final int USER_PASSIVE_SKILL_COUNT = 5;

    public static final int MANA_BONUS_BIT_TIMEOUT = 1;
    public static final int MANA_BONUS_BIT_SIZE = 100; // was 20
    public static final int MANA_BONUS_NUM_BITS = 4;

    public static final int SKILL_CASE_PRICE = 100;

    public static final int MAX_DAILY_SKILL_AMOUNT = 1 << 8;
    public static final int DAILY_SKILLS_COUNT = 3;

    public static final String CHOSEN_BATTLE_BUILDER = "CHOSEN_BATTLE_BUILDER";

    private GameConstants() {}
}
