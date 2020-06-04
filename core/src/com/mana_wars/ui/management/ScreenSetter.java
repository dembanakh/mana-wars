package com.mana_wars.ui.management;

import java.util.Map;

public interface ScreenSetter {
    void setScreen(ScreenInstance screenInstance, Map<String, Object> arguments);
}
