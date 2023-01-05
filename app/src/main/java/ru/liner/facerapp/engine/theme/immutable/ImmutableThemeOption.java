package ru.liner.facerapp.engine.theme.immutable;


import ru.liner.facerapp.engine.theme.ThemeOption;

/* loaded from: classes.dex */
public class ImmutableThemeOption<T> implements ThemeOption<T> {
    private final String name;
    private final T value;

    public ImmutableThemeOption(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeOption
    public String getName() {
        return this.name;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeOption
    public T value() {
        return this.value;
    }
}
