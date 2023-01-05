package ru.liner.facerapp.engine.theme;

/* loaded from: classes.dex */
public interface ThemeTarget<T> {

    /* loaded from: classes.dex */
    public enum Type {
        LAYER,
        LAYER_LIST
    }

    Type getType();

    T getValue();

    boolean isEmpty();

    boolean matches(Type type, String str);
}
