package ru.liner.facerapp.engine.theme.immutable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.liner.facerapp.engine.theme.ThemeOption;
import ru.liner.facerapp.engine.theme.ThemeProperty;
import ru.liner.facerapp.engine.theme.ThemeTarget;

/* loaded from: classes.dex */
public class ImmutableThemeProperty<T> implements ThemeProperty<T> {
    private final List<ThemeOption<T>> options;
    private final String propertyID;
    private final ThemeTarget<T> target;
    private final Type type;

    public ImmutableThemeProperty(String propertyID, Type type, ThemeTarget<T> target, List<ThemeOption<T>> options) {
        this.propertyID = propertyID;
        this.type = type;
        this.target = target;
        this.options = options != null ? Collections.unmodifiableList(options) : null;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeProperty
    public String getID() {
        return this.propertyID;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeProperty
    public Type getType() {
        return this.type;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeProperty
    public ThemeTarget getTarget() {
        return this.target;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeProperty
    public List<ThemeOption<T>> getOptions() {
        return this.options;
    }

    @Override
    public String toString() {
        return "ImmutableThemeProperty{" +
                "options=" + Arrays.toString(options.toArray()) +
                ", propertyID='" + propertyID + '\'' +
                ", target=" + target +
                ", type=" + type +
                '}';
    }
}
