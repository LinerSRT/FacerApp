package ru.liner.facerapp.engine.script.dependency;

import ru.liner.facerapp.engine.script.ScriptEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class LegacyStringScriptProperty  extends LegacyScriptedProperty<String> {
    public LegacyStringScriptProperty(ScriptEngine<String, String> engine) {
        super(engine);
    }

    public LegacyStringScriptProperty(ScriptEngine<String, String> engine, String script, String defaultValue) {
        super(engine, script, defaultValue);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.script.dependency.LegacyScriptedProperty
    public String convertStringToType(String convertable) {
        return convertable;
    }
}