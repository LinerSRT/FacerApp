package ru.liner.facerapp.engine.theme;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SimpleThemeConfiguration<T> implements ThemeConfiguration<T>{
    private static final String DEFAULT_OPTION = "Default";
    private final HashMap<String, T> valuesMap = new HashMap<>();
    private List<ThemeProperty<T>> themePropertyList = null;

    @Override
    public synchronized T getPropertyValue(String key) {
        return valuesMap.isEmpty()? null : valuesMap.get(key);
    }

    public synchronized Map<String, T> getPropertyValueMap() {
        return Collections.unmodifiableMap(valuesMap);
    }


    public synchronized List<ThemeProperty<T>> getThemePropertyList() {
        return themePropertyList == null ? null : themePropertyList;
    }

    @Override
    public synchronized void initializeWithProperties(List<ThemeProperty<T>> list) {
        this.valuesMap.clear();
        this.themePropertyList = list;
        if (themePropertyList != null && !themePropertyList.isEmpty()) {
            for (ThemeProperty<T> property : themePropertyList) {
                String id = property.getID();
                valuesMap.put(id, getDefaultPropertyValue(property));
            }
        }

    }

    @Override
    public synchronized void setPropertyValue(String key, T value) {
        if(key != null){
            if(!key.trim().equals("") || value != null)
                valuesMap.put(key, value);
        }
    }

    public static <T> T getDefaultPropertyValue(@NonNull ThemeProperty<T> property) {
        List<ThemeOption<T>> options = property.getOptions();
        if (options != null && !options.isEmpty())
            for (ThemeOption<T> option : options)
                if (option.getName().equals(DEFAULT_OPTION))
                    return option.value();
        return null;
    }

    public static <T> String getIdForType(List<ThemePropertyValue<T>> propertyValues, @NonNull ThemeProperty.Type type) {
        if (propertyValues != null && !propertyValues.isEmpty())
            for (ThemePropertyValue<T> propertyValue : propertyValues)
                if (type.equals(propertyValue.getType()))
                    return propertyValue.getID();
        return null;
    }

}
