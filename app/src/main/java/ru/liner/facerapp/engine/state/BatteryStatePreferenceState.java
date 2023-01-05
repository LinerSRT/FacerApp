package ru.liner.facerapp.engine.state;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.model.BatteryState;
import ru.liner.facerapp.engine.model.BatteryStateFactory;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class BatteryStatePreferenceState extends PreservedPreferenceState<BatteryState> {
    public BatteryStatePreferenceState(@NonNull Context context, @NonNull String preferenceKey) {
        super(context, preferenceKey);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String convertToString(BatteryState value) {
        return new Gson().toJson(value);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jeremysteckling.facerrel.lib.sync.local.provenance.state.PreservedPreferenceState
    public BatteryState convertToValue(String string) {
        if (string != null) {
            return new Gson().fromJson(string, BatteryState.class);
        }
        return null;
    }
}