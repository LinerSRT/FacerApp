package ru.liner.facerapp.engine.script.dependency;

import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.utils.MathUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class RangeConstraintFloatProperty extends LegacyFloatScriptProperty {
    private float destMax;
    private float destMin;
    private float srcMax;
    private float srcMin;

    public RangeConstraintFloatProperty(ScriptEngine<String, String> engine) {
        super(engine);
        this.srcMin = 0.0f;
        this.srcMax = 1.0f;
        this.destMin = 0.0f;
        this.destMax = 1.0f;
    }

    public RangeConstraintFloatProperty(ScriptEngine<String, String> engine, String script, float defaultValue) {
        super(engine, script, defaultValue);
        this.srcMin = 0.0f;
        this.srcMax = 1.0f;
        this.destMin = 0.0f;
        this.destMax = 1.0f;
    }

    public RangeConstraintFloatProperty(ScriptEngine<String, String> engine, String script, float defaultValue, float inputMin, float inputMax, float targetMin, float targetMax) {
        super(engine, script, defaultValue);
        this.srcMin = 0.0f;
        this.srcMax = 1.0f;
        this.destMin = 0.0f;
        this.destMax = 1.0f;
        this.srcMin = inputMin;
        this.srcMax = inputMax;
        this.destMin = targetMin;
        this.destMax = targetMax;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.ScriptedProperty, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.Dependency
    public synchronized Float get() {
        Float result;
        result = (Float) super.get();
        return Float.valueOf((float) MathUtils.constrainToRange(result.floatValue(), this.srcMin, this.srcMax, this.destMin, this.destMax));
    }

    public synchronized void setInputMin(float inputMin) {
        this.srcMin = inputMin;
    }

    public synchronized void setInputMax(float inputMax) {
        this.srcMax = inputMax;
    }

    public synchronized void setTargetMin(float targetMin) {
        this.destMin = targetMin;
    }

    public synchronized void setTargetMax(float targetMax) {
        this.destMax = targetMax;
    }
}