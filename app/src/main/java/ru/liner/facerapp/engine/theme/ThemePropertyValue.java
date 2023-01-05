package ru.liner.facerapp.engine.theme;


/* loaded from: classes.dex */
public interface ThemePropertyValue<T> {
    T getDefaultValue();

    String getID();

    long getLastModifiedMillis();

    ThemeProperty.Type getType();

    T getValue();

    void setValue(T t);
}
