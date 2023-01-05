package ru.liner.facerapp.engine.script.dependency;

import ru.liner.facerapp.engine.script.ScriptEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class AlphaFloatProperty extends RangeConstraintFloatProperty {
    public AlphaFloatProperty(ScriptEngine<String, String> engine) {
        super(engine, null, 100.0f, 0.0f, 100.0f, 0.0f, 1.0f);
    }

    public AlphaFloatProperty(ScriptEngine<String, String> engine, String script, float defaultValue) {
        super(engine, script, defaultValue, 0.0f, 100.0f, 0.0f, 1.0f);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.script.dependency.RangeConstraintFloatProperty
    public synchronized void setInputMin(float inputMin) {
        super.setInputMin(0.0f);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.script.dependency.RangeConstraintFloatProperty
    public synchronized void setInputMax(float inputMax) {
        super.setInputMax(100.0f);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.script.dependency.RangeConstraintFloatProperty
    public synchronized void setTargetMin(float targetMin) {
        super.setTargetMin(0.0f);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.script.dependency.RangeConstraintFloatProperty
    public synchronized void setTargetMax(float targetMax) {
        super.setTargetMax(1.0f);
    }
}
