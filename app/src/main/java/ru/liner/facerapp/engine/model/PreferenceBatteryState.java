package ru.liner.facerapp.engine.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import ru.liner.facerapp.R;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class PreferenceBatteryState implements BatteryState {
    private static final String SHARED_PREFS_LOCATION = "prefs";
    private int chargePlug;
    private final Context context;
    private long lastModifiedTime;
    private int level;
    private int scale;
    private int status;
    private int temperature;
    private int voltage;

    private PreferenceBatteryState(Context context, int status, int chargePlug, int level, int scale, int temperature, int voltage, long lastModifiedTime) {
        this.status = -1;
        this.chargePlug = -1;
        this.level = -1;
        this.scale = -1;
        this.temperature = -1;
        this.voltage = -1;
        this.lastModifiedTime = 0L;
        this.context = context;
        this.status = status;
        this.chargePlug = chargePlug;
        this.level = level;
        this.scale = scale;
        this.temperature = temperature;
        this.voltage = voltage;
        this.lastModifiedTime = lastModifiedTime;
    }

    public static PreferenceBatteryState fromSharedPrefs(Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int status = prefs.getInt(context.getString(R.string.battery_status), -1);
        int chargePlug = prefs.getInt(context.getString(R.string.battery_chargeplug), -1);
        int level = prefs.getInt(context.getString(R.string.battery_level), -1);
        int scale = prefs.getInt(context.getString(R.string.battery_scale) , -1);
        int temperature = prefs.getInt(context.getString(R.string.battery_temperature) , -1);
        int voltage = prefs.getInt(context.getString(R.string.battery_voltage) , -1);
        long lastModifiedTime = prefs.getLong(context.getString(R.string.battery_lastModified), 0L);
        return new PreferenceBatteryState(context, status, chargePlug, level, scale, temperature, voltage, lastModifiedTime);
    }


    public synchronized void setStatus(int status) {
        this.status = status;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        prefs.edit().putInt(this.context.getString(R.string.battery_status), status).apply();
        updateLastModifiedTime();
    }

    @Override // com.jeremysteckling.facerrel.lib.model.BatteryState
    public synchronized int getStatus() {
        return this.status;
    }

    public synchronized void setChargePlug(int chargePlug) {
        this.chargePlug = chargePlug;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        prefs.edit().putInt(this.context.getString(R.string.battery_chargeplug), chargePlug).apply();
        updateLastModifiedTime();
    }

    public synchronized int getChargePlug() {
        return this.chargePlug;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.BatteryState
    public synchronized boolean isCharging() {
        boolean z;
        if (this.status != 2) {
            if (this.status != 5) {
                z = false;
            }
        }
        z = true;
        return z;
    }

    public synchronized void setLevel(int level) {
        this.level = level;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        prefs.edit().putInt(this.context.getString(R.string.battery_level), level).apply();
        updateLastModifiedTime();
    }

    @Override // com.jeremysteckling.facerrel.lib.model.BatteryState
    public synchronized int getLevel() {
        return this.level;
    }

    public synchronized void setScale(int scale) {
        this.scale = scale;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        prefs.edit().putInt(this.context.getString(R.string.battery_scale), scale).apply();
        updateLastModifiedTime();
    }

    @Override // com.jeremysteckling.facerrel.lib.model.BatteryState
    public synchronized int getScale() {
        return this.scale;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.BatteryState
    public synchronized float getPercentage() {
        float f;
        if (this.level <= 0 || this.scale <= 0) {
            f = 0.0f;
        } else {
            f = this.level / this.scale;
        }
        return f;
    }

    public synchronized void setTemperature(int temperature) {
        this.temperature = temperature;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        prefs.edit().putInt(this.context.getString(R.string.battery_temperature), temperature).apply();
        updateLastModifiedTime();
    }

    @Override // com.jeremysteckling.facerrel.lib.model.BatteryState
    public synchronized int getTemperature() {
        return this.temperature;
    }

    public synchronized void setVoltage(int voltage) {
        this.voltage = voltage;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        prefs.edit().putInt(this.context.getString(R.string.battery_voltage), voltage).apply();
        updateLastModifiedTime();
    }

    @Override // com.jeremysteckling.facerrel.lib.model.BatteryState
    public synchronized int getVoltage() {
        return this.voltage;
    }

    private synchronized void updateLastModifiedTime() {
        this.lastModifiedTime = System.currentTimeMillis();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        prefs.edit().putLong(this.context.getString(R.string.battery_lastModified), this.lastModifiedTime).apply();
    }

    @Override // com.jeremysteckling.facerrel.lib.model.BatteryState
    public synchronized long getLastModifiedTime() {
        return this.lastModifiedTime;
    }
}