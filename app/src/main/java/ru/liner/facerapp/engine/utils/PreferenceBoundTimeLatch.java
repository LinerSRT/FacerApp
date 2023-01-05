package ru.liner.facerapp.engine.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class PreferenceBoundTimeLatch {
    private final long intervalMillis;
    private long lastUpdatedTime;
    private final String preferenceName;

    public PreferenceBoundTimeLatch(Context context, String preferenceName, long interval, @NonNull TimeUnit timeUnit) {
        SharedPreferences preferences;
        this.lastUpdatedTime = 0L;
        this.preferenceName = preferenceName;
        this.intervalMillis = timeUnit.toMillis(interval);
        if (context != null && (preferences = PreferenceManager.getDefaultSharedPreferences(context)) != null) {
            this.lastUpdatedTime = preferences.getLong(preferenceName, 0L);
        }
    }

    public synchronized boolean isReleased() {
        return this.lastUpdatedTime + this.intervalMillis < System.currentTimeMillis();
    }

    public synchronized long getTimeRemaining() {
        return (this.lastUpdatedTime + this.intervalMillis) - System.currentTimeMillis();
    }

    public synchronized void latch(Context context) {
        SharedPreferences preferences;
        this.lastUpdatedTime = System.currentTimeMillis();
        if (context != null && (preferences = PreferenceManager.getDefaultSharedPreferences(context)) != null) {
            preferences.edit().putLong(this.preferenceName, this.lastUpdatedTime).apply();
        }
    }

}
