package ru.liner.facerapp.engine.state;

import android.content.Context;

import androidx.annotation.NonNull;

import ru.liner.facerapp.wrapper.PM;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public abstract class PreservedPreferenceState<T> extends PreservedState<T> {
    private final Context context;
    private final String preferenceKey;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract String convertToString(T value);

    protected abstract T convertToValue(String string);

    public PreservedPreferenceState(@NonNull Context context, @NonNull String preferenceKey) {
        this.context = context.getApplicationContext();
        this.preferenceKey = preferenceKey;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jeremysteckling.facerrel.lib.sync.local.provenance.PreservedState
    public boolean saveState(T state) {
        PM.put(preferenceKey, convertToString(state));
        return true;
    }

    @Override // com.jeremysteckling.facerrel.lib.sync.local.provenance.PreservedState
    protected T loadState() {
        return convertToValue(PM.get(preferenceKey, ""));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Context getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getPreferenceKey() {
        return this.preferenceKey;
    }
}