package ru.liner.facerapp.engine.script;

public interface ScriptEngine<I, O> {
    int[] checkFeatures(I input);
    boolean isDynamic(I input);
    O parse(I input);
    void shutdown();
    void update(long currentTimeMillis);
    void update(boolean forceUpdate);
}
