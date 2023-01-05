package ru.liner.facerapp.engine;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import ru.liner.facerapp.engine.state.PreservedPreferenceState;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class SyncedSettingsController {
    private static final HourMode hourModeState = HourMode.TWENTYFOUR_HOUR;


    /* loaded from: classes2.dex */
    public enum HourMode {
        AUTOMATIC,
        TWELVE_HOUR,
        TWENTYFOUR_HOUR
    }

    private SyncedSettingsController() {
    }

    public static HourMode getHourMode(Context context) {
    return hourModeState;
    }

    protected static HourMode getDefaultHourMode(Context context) {
        return hourModeState;
    }
}
