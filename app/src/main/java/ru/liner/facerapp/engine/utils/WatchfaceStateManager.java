package ru.liner.facerapp.engine.utils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class WatchfaceStateManager {
    private static MODE currentState = MODE.STANDARD;

    /* loaded from: classes2.dex */
    public enum MODE {
        AMBIENT,
        STANDARD,
        DETAILED
    }

    public static synchronized void setCurrentMode(MODE stateMode) {
        synchronized (WatchfaceStateManager.class) {
            currentState = stateMode;
        }
    }

    public static synchronized MODE getCurrentState() {
        MODE mode;
        synchronized (WatchfaceStateManager.class) {
            mode = currentState;
        }
        return mode;
    }
}