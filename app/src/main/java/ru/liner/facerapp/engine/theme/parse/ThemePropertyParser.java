package ru.liner.facerapp.engine.theme.parse;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.theme.ThemeOption;
import ru.liner.facerapp.engine.theme.ThemeProperty;
import ru.liner.facerapp.engine.theme.ThemeTarget;
import ru.liner.facerapp.engine.theme.immutable.ImmutableThemeProperty;

/* loaded from: classes.dex */
public class ThemePropertyParser {
    public static final String ID = "uid";
    public static final String OPTIONS = "options";
    public static final String TARGET = "target";
    public static final String TYPE = "type";
    public static final String TYPE_COLOR = "color";

    public ThemeProperty<String> parse(@NonNull JSONObject propertyJson) throws JSONException {
        String id = parsePropertyID(propertyJson);
        ThemeProperty.Type type = parseType(propertyJson);
        ThemeTarget target = parseTarget(propertyJson);
        List<ThemeOption<String>> options = parseOptions(propertyJson);
        if (id != null && type != null) {
            return new ImmutableThemeProperty(id, type, target, options);
        }
        Log.e(ThemePropertyParser.class.getSimpleName(), "Failed to parse ThemeProperty from JSON: " + propertyJson.toString());
        return null;
    }

    public JSONObject parse(@NonNull ThemeProperty<String> property) throws JSONException {
        JSONObject propertyJson = new JSONObject();
        propertyJson.put("uid", property.getID());
        propertyJson.put("type", property.getType());
        ThemeTarget target = property.getTarget();
        if (target != null) {
            propertyJson.put(TARGET, new ThemeTargetParser().parse(target));
        }
        List<ThemeOption<String>> options = property.getOptions();
        if (options != null && !options.isEmpty()) {
            ThemeOptionParser optionParser = new ThemeOptionParser();
            JSONArray optionsJson = new JSONArray();
            for (ThemeOption<String> option : options) {
                optionsJson.put(optionParser.parse(option));
            }
            propertyJson.put(OPTIONS, optionsJson);
        }
        return propertyJson;
    }

    protected String parsePropertyID(@NonNull JSONObject propertyJson) throws JSONException {
        if (propertyJson.has("uid")) {
            return propertyJson.getString("uid");
        }
        return null;
    }

    protected ThemeProperty.Type parseType(@NonNull JSONObject propertyJson) throws JSONException {
        if (propertyJson.has("type")) {
            String type = propertyJson.getString("type");
            char c = 65535;
            switch (type.hashCode()) {
                case 94842723:
                    if (type.equals("color")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return ThemeProperty.Type.COLOR;
            }
        }
        return null;
    }

    protected ThemeTarget parseTarget(@NonNull JSONObject propertyJson) throws JSONException {
        if (!propertyJson.has(TARGET)) {
            return null;
        }
        return new ThemeTargetParser().parse(propertyJson.getJSONObject(TARGET));
    }

    protected List<ThemeOption<String>> parseOptions(@NonNull JSONObject propertyJson) throws JSONException {
        if (!propertyJson.has(OPTIONS)) {
            return null;
        }
        List<ThemeOption<String>> options = new ArrayList<>();
        JSONArray optionsJSON = propertyJson.getJSONArray(OPTIONS);
        if (optionsJSON == null || optionsJSON.length() <= 0) {
            return options;
        }
        ThemeOptionParser parser = new ThemeOptionParser();
        for (int i = 0; i < optionsJSON.length(); i++) {
            ThemeOption<String> option = parser.parse(optionsJSON.getJSONObject(i));
            if (option != null) {
                options.add(option);
            }
        }
        return options;
    }
}
