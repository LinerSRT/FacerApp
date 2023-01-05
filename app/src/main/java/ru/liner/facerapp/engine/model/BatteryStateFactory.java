package ru.liner.facerapp.engine.model;

import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class BatteryStateFactory {
    public static final String LAST_MODIFIED = "lastModified";
    public static final String LEVEL = "level";
    public static final String SCALE = "scale";
    public static final String STATUS = "status";
    public static final String TEMPERATURE = "temperature";
    public static final String VOLTAGE = "voltage";

    public static JSONObject toJSONObject(BatteryState batteryState) {
        JSONObject output = new JSONObject();
        try {
            output.put("status", batteryState.getStatus());
            output.put(LEVEL, batteryState.getLevel());
            output.put(SCALE, batteryState.getScale());
            output.put(TEMPERATURE, batteryState.getTemperature());
            output.put(VOLTAGE, batteryState.getVoltage());
            output.put(LAST_MODIFIED, batteryState.getLastModifiedTime());
            return output;
        } catch (JSONException e) {
            Log.w(BatteryStateFactory.class.getSimpleName(), "Encountered a JSONException while attempting to construct a JSONObject from a BatteryState object; returning null.", e);
            return null;
        }
    }

    public static BatteryState fromJSONObject(JSONObject json) {
        try {
            BatteryState output = new ImmutableBatteryState(json.getInt("status"), json.getInt(LEVEL), json.getInt(SCALE), json.getInt(TEMPERATURE), json.getInt(VOLTAGE), json.getLong(LAST_MODIFIED));
            return output;
        } catch (JSONException e) {
            Log.w(BatteryStateFactory.class.getSimpleName(), "Encountered a JSONException while attempting to construct a BatteryState from a JSONObject; returning null.", e);
            return null;
        }
    }

    public static BatteryState fromIntent(Intent intent) {
        int status = -1;
        int level = -1;
        int scale = -1;
        int temperature = -1;
        int voltage = -1;
        if (intent.hasExtra("status")) {
            status = intent.getIntExtra("status", -1);
        }
        if (intent.hasExtra("plugged")) {
            intent.getIntExtra("plugged", -1);
        }
        if (intent.hasExtra(LEVEL)) {
            level = intent.getIntExtra(LEVEL, -1);
        }
        if (intent.hasExtra(SCALE)) {
            scale = intent.getIntExtra(SCALE, -1);
        }
        if (intent.hasExtra(TEMPERATURE)) {
            temperature = intent.getIntExtra(TEMPERATURE, -1);
        }
        if (intent.hasExtra(VOLTAGE)) {
            voltage = intent.getIntExtra(VOLTAGE, -1);
        }
        return new ImmutableBatteryState(status, level, scale, temperature, voltage, System.currentTimeMillis());
    }

}
