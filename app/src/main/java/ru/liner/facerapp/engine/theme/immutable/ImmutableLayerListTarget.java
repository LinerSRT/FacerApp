package ru.liner.facerapp.engine.theme.immutable;

import android.util.Log;
import java.util.Collections;
import java.util.List;

import ru.liner.facerapp.engine.theme.ThemeTarget;

/* loaded from: classes.dex */
public class ImmutableLayerListTarget implements ThemeTarget<List<String>> {
    private final List<String> layerList;

    public ImmutableLayerListTarget(List<String> layerList) {
        this.layerList = layerList;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeTarget
    public Type getType() {
        return Type.LAYER_LIST;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeTarget
    public List<String> getValue() {
        return Collections.unmodifiableList(this.layerList);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeTarget
    public boolean matches(Type targetType, String id) {
        if (this.layerList == null || this.layerList.isEmpty()) {
            Log.v(ImmutableLayerListTarget.class.getSimpleName(), "Failed to match against Layer List; list was empty.");
            return false;
        }
        Log.v(ImmutableLayerListTarget.class.getSimpleName(), "Checking for Layer List ThemeTarget match against [" + targetType.name() + "] [" + id + "]; has [" + this.layerList.size() + "] potential targets.");
        if (Type.LAYER.equals(targetType)) {
            Log.v(ImmutableLayerListTarget.class.getSimpleName(), "[" + (this.layerList.contains(id) ? "Matched" : "Failed to match") + "] layerID [" + id + "]");
            return this.layerList.contains(id);
        }
        Log.w(ImmutableLayerListTarget.class.getSimpleName(), "Cannot check for match, invalid target type.");
        return false;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.theme.ThemeTarget
    public boolean isEmpty() {
        return this.layerList == null || this.layerList.isEmpty();
    }
}
