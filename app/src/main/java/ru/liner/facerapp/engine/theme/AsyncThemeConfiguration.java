package ru.liner.facerapp.engine.theme;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class AsyncThemeConfiguration implements ThemeConfiguration<String> {
    public static final String PREFS_SEPARATOR = ".";
    public static final String PREFS_THEMECONFIG_STATE = "ThemeConfigurationDataSourceState";
    private final ThemePropertyPreferenceState valueState;

    public AsyncThemeConfiguration(@NonNull Context context, String instanceID) {
        valueState = new ThemePropertyPreferenceState(context, instanceID);

    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeConfiguration
    public synchronized String getPropertyValue(String key) {
        return this.valueState != null ? this.valueState.getPropertyValue(key) : null;
    }

    public synchronized void setPropertyValue(String key, String value) {
        if (this.valueState != null) {
            this.valueState.setPropertyValue(key, value);
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeConfiguration
    public String getDefaultPropertyValue(String key) {
        if (this.valueState != null) {
            return this.valueState.getDefaultPropertyValue(key);
        }
        return null;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeConfiguration
    public List<ThemePropertyValue<String>> getPropertyValues() {
        if (this.valueState != null) {
            return Collections.unmodifiableList(this.valueState.get());
        }
        return null;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeConfiguration
    public synchronized void initializeWithProperties(List<ThemeProperty<String>> properties) {
        List<ThemePropertyValue<String>> values = valueState.get();
        if(values == null)
            values = new ArrayList<>();
        if (properties == null || properties.isEmpty()) {
            values.clear();
        } else {
            for (ThemeProperty<String> property : properties) {
                String id = property.getID();
                ThemePropertyValue<String> existingValue = getValueById(values, id);
                if (existingValue == null) {
                    values.add(new StringThemePropertyValue(id, property.getType(), property.getOptions().get(0).value(), (String) SimpleThemeConfiguration.getDefaultPropertyValue(property)));
                } else {
                    String defaultSavedValue = existingValue.getDefaultValue();
                    String defaultPropertyValue = (String) SimpleThemeConfiguration.getDefaultPropertyValue(property);
                    if (!defaultSavedValue.equals(defaultPropertyValue)) {
                        values.remove(existingValue);
                        values.add(new StringThemePropertyValue(id, property.getType(), property.getOptions().get(0).value(), defaultPropertyValue));
                    }
                }
            }
        }
        valueState.set(values);
    }
    private static ThemePropertyValue<String> getValueById(List<ThemePropertyValue<String>> values, String id) {
        for (ThemePropertyValue<String> value : values) {
            if (value.getID().equals(id)) {
                return value;
            }
        }
        return null;
    }
    protected boolean containsValueID(List<ThemePropertyValue<String>> properties, String propertyID) {
        if (properties != null && !properties.isEmpty() && propertyID != null) {
            for (ThemePropertyValue<String> property : properties) {
                if (propertyID.equals(property.getID())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getPrefsPrefix() {
        return "ThemeConfigurationDataSourceState.";
    }
}