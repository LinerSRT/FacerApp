package ru.liner.facerapp.engine.state;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class StringPreferenceState extends PreservedPreferenceState<String> {
    public StringPreferenceState(@NonNull Context context, @NonNull String preferenceKey) {
        super(context, preferenceKey);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String convertToString(String value) {
        return value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jeremysteckling.facerrel.lib.sync.local.provenance.state.PreservedPreferenceState
    public String convertToValue(String string) {
        return string;
    }
}