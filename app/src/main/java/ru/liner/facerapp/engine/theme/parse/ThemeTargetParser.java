package ru.liner.facerapp.engine.theme.parse;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.theme.ThemeTarget;
import ru.liner.facerapp.engine.theme.immutable.ImmutableLayerListTarget;

/* loaded from: classes.dex */
public class ThemeTargetParser {
    public static final String TYPE = "type";
    public static final String TYPE_LAYER_LIST = "layer_list";
    public static final String VALUE = "value";

    public ThemeTarget parse(@NonNull JSONObject targetJson) throws JSONException {
        switch (parseType(targetJson)) {
            case LAYER_LIST:
                return parseLayerList(targetJson);
            default:
                return null;
        }
    }

    public JSONObject parse(@NonNull ThemeTarget target) throws JSONException {
        switch (target.getType()) {
            case LAYER_LIST:
                return parseLayerList(target);
            default:
                return null;
        }
    }

    protected ThemeTarget.Type parseType(@NonNull JSONObject targetJson) throws JSONException {
        if (targetJson.has("type")) {
            String type = targetJson.getString("type");
            char c = 65535;
            switch (type.hashCode()) {
                case -48021812:
                    if (type.equals(TYPE_LAYER_LIST)) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return ThemeTarget.Type.LAYER_LIST;
            }
        }
        return null;
    }

    protected ThemeTarget parseLayerList(@NonNull JSONObject targetJson) throws JSONException {
        JSONArray valueJson;
        if (!targetJson.has("value") || (valueJson = targetJson.getJSONArray("value")) == null || valueJson.length() <= 0) {
            return null;
        }
        List<String> layerIDs = new ArrayList<>();
        for (int i = 0; i < valueJson.length(); i++) {
            layerIDs.add(valueJson.getString(i));
        }
        return new ImmutableLayerListTarget(layerIDs);
    }

    protected JSONObject parseLayerList(@NonNull ThemeTarget target) throws JSONException {
        List<String> layers;
        JSONObject targetJson = new JSONObject();
        targetJson.put("type", TYPE_LAYER_LIST);
        if ((target instanceof ImmutableLayerListTarget) && (layers = ((ImmutableLayerListTarget) target).getValue()) != null && !layers.isEmpty()) {
            JSONArray layersJson = new JSONArray();
            for (String layerID : layers) {
                layersJson.put(layerID);
            }
            targetJson.put("value", layersJson);
        }
        return targetJson;
    }
}
