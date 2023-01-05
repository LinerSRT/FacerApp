package ru.liner.facerapp.engine.script.dependency;


import ru.liner.facerapp.engine.input.Action;
import ru.liner.facerapp.engine.input.ClickableProperty;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;


public abstract class ScriptedProperty<T> extends Dependency<T> implements ClickableProperty {
    private Action clickAction;
    private T defaultValue;
    private T evaluatedData;
    private boolean isClickable;
    private boolean isDynamic;
    private String script;

    protected abstract void checkFeatures(String str);

    protected abstract T evaluate(String str, T t);

    protected abstract boolean isDynamic(String str);

    public ScriptedProperty() {
        this(null, null);
    }

    public ScriptedProperty(String script, T defaultValue) {
        this.script = null;
        this.isDynamic = false;
        this.evaluatedData = null;
        this.defaultValue = null;
        this.isClickable = false;
        this.clickAction = null;
        setDefaultValue(defaultValue);
        setScript(script);
    }

    public synchronized void setScript(String script) {
        this.script = script;
        this.evaluatedData = evaluate(script, this.defaultValue);
        this.isDynamic = isDynamic(script);
        checkFeatures(script);
        invalidate();
    }

    public synchronized void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        this.evaluatedData = evaluate(this.script, defaultValue);
        invalidate();
    }

    protected synchronized T getDefaultValue() {
        return this.defaultValue;
    }

    @Override 
    public synchronized T get() {
        return this.evaluatedData;
    }

    @Override 
    public synchronized boolean isInvalidated() {
        boolean z;
        if (!this.isDynamic) {
            if (!super.isInvalidated()) {
                return false;
            }
        }
        z = true;
        return true;
    }

    
    @Override 
    public synchronized void updateSelf(long currentTimeMillis) {
        this.evaluatedData = evaluate(this.script, this.defaultValue);
    }

    protected void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    @Override 
    public boolean isClickable() {
        return this.isClickable;
    }

    public void setClickAction(Action clickAction) {
        this.clickAction = clickAction;
    }

    @Override 
    public Action getClickAction() {
        return this.clickAction;
    }
}
