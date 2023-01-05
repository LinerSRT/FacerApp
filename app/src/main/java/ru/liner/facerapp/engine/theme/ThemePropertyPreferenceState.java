package ru.liner.facerapp.engine.theme;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ru.liner.facerapp.engine.state.PreservedPreferenceState;
import ru.liner.facerapp.wrapper.PM;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ThemePropertyPreferenceState extends PreservedPreferenceState<List<ThemePropertyValue<String>>> {
    public ThemePropertyPreferenceState(@NonNull Context context, @NonNull String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public String convertToString(List<ThemePropertyValue<String>> value) {
        if (value == null || value.isEmpty())
            return null;
        return new Gson().toJson(value);
    }

    @Override
    protected List<ThemePropertyValue<String>> convertToValue(String string) {
        List<ThemePropertyValue<String>> list = new Gson().fromJson(string, new PM.ListTypeToken<>(SimplePropertyValue.class));
        return list == null ? new ArrayList<>() : list;
    }

    public String getPropertyValue(String key) {
        List<ThemePropertyValue<String>> themePropertyValueList = loadState();
        for (ThemePropertyValue<String> propertyValue : themePropertyValueList) {
            if (propertyValue.getID().equals(key))
                return propertyValue.getValue();
        }
        return null;
    }
    public String getDefaultPropertyValue(String key) {
        List<ThemePropertyValue<String>> themePropertyValueList = loadState();
        for (ThemePropertyValue<String> propertyValue : themePropertyValueList) {
            if (propertyValue.getID().equals(key))
                return propertyValue.getDefaultValue();
        }
        return null;
    }

    public void setPropertyValue(String key, String value) {
        List<ThemePropertyValue<String>> themePropertyValueList = loadState();
        for (ThemePropertyValue<String> propertyValue : themePropertyValueList) {
            if (propertyValue.getID().equals(key))
                propertyValue.setValue(value);
        }
        saveState(themePropertyValueList);
    }
}
