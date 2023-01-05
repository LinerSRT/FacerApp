package ru.liner.facerapp.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 29.12.2021, среда
 **/
public class JsonReader {

    public static String getString(JSONObject jsonObject, String key, String defaultValue){
        if(jsonObject == null || !jsonObject.has(key)) return defaultValue;
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static int getInt(JSONObject jsonObject, String key, int defaultValue){
        if(jsonObject == null || !jsonObject.has(key)) return defaultValue;
        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static double getDouble(JSONObject jsonObject, String key, double defaultValue){
        if(jsonObject == null || !jsonObject.has(key)) return defaultValue;
        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static boolean getBoolean(JSONObject jsonObject, String key, boolean defaultValue){
        if(jsonObject == null || !jsonObject.has(key)) return defaultValue;
        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static long getLong(JSONObject jsonObject, String key, long defaultValue){
        if(jsonObject == null || !jsonObject.has(key)) return defaultValue;
        try {
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
