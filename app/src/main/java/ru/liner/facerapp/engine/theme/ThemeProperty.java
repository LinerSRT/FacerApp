package ru.liner.facerapp.engine.theme;

import java.util.List;

/* loaded from: classes.dex */
public interface ThemeProperty<T> {

    /* loaded from: classes.dex */
    public enum Type {
        COLOR
    }

    String getID();

    List<ThemeOption<T>> getOptions();

    ThemeTarget<T> getTarget();

    Type getType();
}
