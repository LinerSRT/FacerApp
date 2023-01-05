package ru.liner.facerapp.engine.theme.parse;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.theme.ThemeOption;
import ru.liner.facerapp.engine.theme.immutable.ImmutableThemeOption;

/* loaded from: classes.dex */
public class ThemeOptionParser {
    public static final String NAME = "name";
    public static final String VALUE = "value";

    public ThemeOption<String> parse(@NonNull JSONObject optionJson) throws JSONException {
        return new ImmutableThemeOption(parseName(optionJson), parseValue(optionJson));
    }

    public JSONObject parse(@NonNull ThemeOption<String> themeOption) throws JSONException {
        JSONObject optionJson = new JSONObject();
        optionJson.put("name", themeOption.getName());
        optionJson.put("value", themeOption.value());
        return optionJson;
    }

    protected String parseName(@NonNull JSONObject optionJson) throws JSONException {
        if (optionJson.has("name")) {
            return optionJson.getString("name");
        }
        return null;
    }

    protected String parseValue(@NonNull JSONObject optionJson) throws JSONException {
        if (optionJson.has("value")) {
            return optionJson.getString("value");
        }
        return null;
    }
}
