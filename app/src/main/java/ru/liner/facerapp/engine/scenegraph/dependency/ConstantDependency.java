package ru.liner.facerapp.engine.scenegraph.dependency;


public class ConstantDependency<T> extends Dependency<T> {
    private T value;

    public ConstantDependency(T value) {
        this.value = value;
    }

    
    @Override 
    public void updateSelf(long currentTimeMillis) {
    }

    @Override 
    public T get() {
        return this.value;
    }

    @Override 
    public synchronized void invalidate() {
    }

    @Override 
    public synchronized boolean isInvalidated() {
        return false;
    }
}
