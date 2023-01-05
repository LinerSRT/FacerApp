package ru.liner.facerapp.engine.theme.parse;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.theme.StringThemePropertyValue;
import ru.liner.facerapp.engine.theme.ThemeProperty;
import ru.liner.facerapp.engine.theme.ThemePropertyValue;

/* loaded from: classes.dex */
public class ThemePropertyValueParser {
    public static final String DEFAULT_VALUE = "defaultValue";
    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String VALUE = "value";

    public ThemePropertyValue<String> parse(@NonNull JSONObject propertyJson) throws JSONException {
        String propertyID = parseID(propertyJson);
        ThemeProperty.Type type = parseType(propertyJson);
        String value = parseValue(propertyJson);
        String defaultValue = parseDefaultValue(propertyJson);
        if (propertyID != null && type != null) {
            return new StringThemePropertyValue(propertyID, type, value, defaultValue);
        }
        Log.e(ThemePropertyValueParser.class.getSimpleName(), "Failed to parse ThemePropertyValue for JSON: " + propertyJson);
        return null;
    }

    public JSONObject parse(@NonNull ThemePropertyValue<String> property) throws JSONException {
        JSONObject propertyJson = new JSONObject();
        propertyJson.put("id", property.getID());
        ThemeProperty.Type type = property.getType();
        if (type != null) {
            propertyJson.put("type", parseType(type));
        }
        propertyJson.put("value", property.getValue());
        propertyJson.put(DEFAULT_VALUE, property.getDefaultValue());
        return propertyJson;
    }

    protected String parseID(@NonNull JSONObject propertyJson) throws JSONException {
        if (propertyJson.has("id")) {
            return propertyJson.getString("id");
        }
        return null;
    }

    protected ThemeProperty.Type parseType(@NonNull JSONObject propertyJson) throws JSONException {
        if (propertyJson.has("type")) {
            try {
                return ThemeProperty.Type.valueOf(propertyJson.getString("type"));
            } catch (IllegalArgumentException e) {
                Log.e(ThemePropertyValueParser.class.getSimpleName(), "Unable to parse type due to exception.", e);
            }
        }
        return null;
    }

    protected String parseType(@NonNull ThemeProperty.Type type) {
        return type.name();
    }

    protected String parseValue(@NonNull JSONObject propertyJson) throws JSONException {
        if (propertyJson.has("value")) {
            return propertyJson.getString("value");
        }
        return null;
    }

    protected String parseDefaultValue(@NonNull JSONObject propertyJson) throws JSONException {
        if (propertyJson.has(DEFAULT_VALUE)) {
            return propertyJson.getString(DEFAULT_VALUE);
        }
        return null;
    }
}
