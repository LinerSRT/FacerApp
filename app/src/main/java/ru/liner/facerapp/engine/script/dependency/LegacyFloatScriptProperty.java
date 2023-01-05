package ru.liner.facerapp.engine.script.dependency;

import ru.liner.facerapp.engine.script.ScriptEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class LegacyFloatScriptProperty extends LegacyScriptedProperty<Float> {
    public LegacyFloatScriptProperty(ScriptEngine<String, String> engine) {
        super(engine);
    }

    public LegacyFloatScriptProperty(ScriptEngine<String, String> engine, String script, float defaultValue) {
        super(engine, script, Float.valueOf(defaultValue));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.script.dependency.LegacyScriptedProperty
    public Float convertStringToType(String convertable) {
        if(convertable == null)
            return 0f;
        Float output = getDefaultValue();
        try {
            Float output2 = Float.valueOf(Float.parseFloat(convertable));
            return output2;
        } catch (NumberFormatException e) {
            return output;
        }
    }
}