package ru.liner.facerapp.engine.theme;

import java.util.List;

public interface ThemeConfiguration<T> {
    T getDefaultPropertyValue(String key);

    T getPropertyValue(String key);

    void setPropertyValue(String key, T value);

    List<ThemePropertyValue<T>> getPropertyValues();

    void initializeWithProperties(List<ThemeProperty<T>> list);

}
