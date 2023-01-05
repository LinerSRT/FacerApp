package ru.liner.facerapp.engine.theme;


/* loaded from: classes.dex */
public class SimplePropertyValue<T> implements ThemePropertyValue<T> {
    private final T defaultValue;
    private long lastUpdatedMillis;
    private final String propertyID;
    private final ThemeProperty.Type type;
    private T value;

    protected boolean testEquality(T t, T t2){
        return true;
    }

    public SimplePropertyValue(String propertyID, ThemeProperty.Type type) {
        this(propertyID, type, null);
    }

    public SimplePropertyValue(String propertyID, ThemeProperty.Type type, T value) {
        this(propertyID, type, value, null);
    }

    public SimplePropertyValue(String propertyID, ThemeProperty.Type type, T value, T defaultValue) {
        this.lastUpdatedMillis = 0;
        this.propertyID = propertyID;
        this.type = type;
        this.value = value;
        this.defaultValue = defaultValue;
        if (value != null) {
            this.lastUpdatedMillis = System.currentTimeMillis();
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemePropertyValue
    public String getID() {
        return this.propertyID;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemePropertyValue
    public ThemeProperty.Type getType() {
        return this.type;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemePropertyValue
    public synchronized T getValue() {
        return this.value;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemePropertyValue
    public synchronized void setValue(T value) {
        if (!testEquality(this.value, value)) {
            this.lastUpdatedMillis = System.currentTimeMillis();
        }
        this.value = value;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemePropertyValue
    public T getDefaultValue() {
        return this.defaultValue;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemePropertyValue
    public long getLastModifiedMillis() {
        return this.lastUpdatedMillis;
    }
}
