package ru.liner.facerapp.engine.scenegraph.dependency;


import androidx.annotation.NonNull;

public abstract class StringTransformDependency extends Dependency<String> {
    private Dependency<String> dependency;
    private String value = null;

    protected abstract String transform(@NonNull String str);

    public StringTransformDependency(Dependency<String> dependency) {
        this.dependency = dependency;
    }

    public synchronized void setDependency(Dependency<String> dependency) {
        this.dependency = dependency;
    }

    @Override 
    public synchronized void invalidate() {
        if (this.dependency != null) {
            this.dependency.invalidate();
        }
        super.invalidate();
    }

    @Override 
    public synchronized int getVersion() {
        int version;
        if (this.dependency != null) {
            version = this.dependency.getVersion();
        } else {
            version = super.getVersion();
        }
        return version;
    }

    @Override 
    public synchronized boolean isInvalidated() {
        boolean z;
        if (this.dependency != null) {
            z = this.dependency.isInvalidated();
        } else {
            z = false;
        }
        return z;
    }

    
    @Override 
    public synchronized void updateSelf(long currentTimeMillis) {
        if (this.dependency != null) {
            this.dependency.updateSelf(currentTimeMillis);
            String dependencyValue = this.dependency.get();
            if (dependencyValue != null) {
                this.value = transform(dependencyValue);
            }
        }
    }

    @Override 
    public synchronized String get() {
        return this.value;
    }
}
