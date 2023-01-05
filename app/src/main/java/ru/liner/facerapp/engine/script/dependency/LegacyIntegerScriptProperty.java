package ru.liner.facerapp.engine.script.dependency;

import ru.liner.facerapp.engine.script.ScriptEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class LegacyIntegerScriptProperty extends LegacyScriptedProperty<Integer> {
    public LegacyIntegerScriptProperty(ScriptEngine<String, String> engine) {
        super(engine);
    }

    public LegacyIntegerScriptProperty(ScriptEngine<String, String> engine, String script, Integer defaultValue) {
        super(engine, script, defaultValue);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.script.dependency.LegacyScriptedProperty
    public Integer convertStringToType(String convertable) {
        Integer output = getDefaultValue();
        try {
            Integer output2 = Integer.valueOf(Integer.parseInt(convertable));
            return output2;
        } catch (NumberFormatException e) {
            return output;
        }
    }
}