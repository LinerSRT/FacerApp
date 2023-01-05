package ru.liner.facerapp.engine.theme;


/* loaded from: classes.dex */
public class StringThemePropertyValue extends SimplePropertyValue<String> {
    public StringThemePropertyValue(String propertyID, ThemeProperty.Type type) {
        super(propertyID, type);
    }

    public StringThemePropertyValue(String propertyID, ThemeProperty.Type type, String value) {
        super(propertyID, type, value);
    }

    public StringThemePropertyValue(String propertyID, ThemeProperty.Type type, String value, String defaultValue) {
        super(propertyID, type, value, defaultValue);
    }

    public boolean testEquality(String left, String right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.equals(right);
    }
}
