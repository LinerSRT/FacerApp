package ru.liner.facerapp.engine.model;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public interface WatchfaceData {
    Bitmap getBitmap(String imageID);

    JSONArray getComplicationJson();

    JSONArray getJsonData();

    int getProtectionVersion();

    JSONObject getSingleLayer(int layerID);

    JSONArray getThemeJson();

    String getWatchfaceID();

    boolean isProtected();

    boolean isRecycled();

    void recycle();

    void setSingleLayer(int layerID, JSONObject jSONObject);
}