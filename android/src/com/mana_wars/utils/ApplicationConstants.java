package com.mana_wars.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class ApplicationConstants {

    public static final SimpleDateFormat DEFAULT_APPLICATION_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    public static final int DB_VERSION = 1;
    public static final int INSTANCES_TO_UPDATE_FROM_JSON = 6;
    public static final String DAILY_SKILLS_JSON_URL = "https://arturkasymov.student.tcs.uj.edu.pl/mana_wars_daily_skills.html";

    private ApplicationConstants() {}
}
