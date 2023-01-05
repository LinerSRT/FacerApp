package ru.liner.facerapp.engine.script.dependency;

import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.script.LegacyScriptEngine;
import ru.liner.facerapp.engine.script.ScriptEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public abstract class LegacyScriptedProperty <T> extends ScriptedProperty<T> {
    private final ScriptEngine<String, String> engine;

    protected abstract T convertStringToType(String str);

    public LegacyScriptedProperty(@NonNull ScriptEngine<String, String> engine) {
        this.engine = engine;
    }

    public LegacyScriptedProperty(@NonNull ScriptEngine<String, String> engine, String script, T defaultValue) {
        super(script, defaultValue);
        this.engine = engine;
        setScript(script);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.ScriptedProperty
    protected synchronized T evaluate(String script, T defaultValue) {
        if (this.engine != null) {
            defaultValue = convertStringToType(this.engine.parse(script));
        }
        return defaultValue;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.ScriptedProperty
    protected synchronized boolean isDynamic(String script) {
        boolean z;
        if (this.engine != null) {
            if (this.engine.isDynamic(script)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.ScriptedProperty
    protected void checkFeatures(String script) {
        if (this.engine != null && script != null) {
            int[] features = this.engine.checkFeatures(script);
            if (this.engine instanceof LegacyScriptEngine) {
                LegacyScriptEngine legacyEngine = (LegacyScriptEngine) this.engine;
                if (features != null && features.length > 0) {
                    for (int i : features) {
                        if (3 == i) {
                            setClickable(true);
                            //setClickAction(legacyEngine.getComplicationClickAction(script));
                        }
                    }
                }
            }
        }
    }
}