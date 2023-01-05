package ru.liner.facerapp.engine.theme.parse;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.theme.ThemeProperty;

/* loaded from: classes.dex */
public class WatchfaceThemeJsonParser {
    public List<ThemeProperty<String>> parseProperties(@NonNull JSONArray themeJson) {
        List<ThemeProperty<String>> properties = new ArrayList<>();
        ThemePropertyParser propertyParser = new ThemePropertyParser();
        for (int i = 0; i < themeJson.length(); i++) {
            try {
                ThemeProperty<String> property = propertyParser.parse(themeJson.getJSONObject(i));
                if (property != null) {
                    properties.add(property);
                }
            } catch (JSONException e) {
                Log.w(WatchfaceThemeJsonParser.class.getSimpleName(), "Unable to parse theme property at index [" + i + "]; skipping, some properties may not appear correctly.", e);
            }
        }
        return properties;
    }

    public JSONArray parseProperties(@NonNull List<ThemeProperty<String>> properties) {
        if (properties.isEmpty()) {
            return null;
        }
        JSONArray themeJson = new JSONArray();
        ThemePropertyParser propertyParser = new ThemePropertyParser();
        for (ThemeProperty<String> property : properties) {
            try {
                JSONObject propertyJson = propertyParser.parse(property);
                if (propertyJson != null) {
                    themeJson.put(propertyJson);
                }
            } catch (JSONException e) {
                Log.w(WatchfaceThemeJsonParser.class.getSimpleName(), "Unable to parse theme property to JSON; skipping, some properties may not save correctly.", e);
            }
        }
        return themeJson;
    }
}
