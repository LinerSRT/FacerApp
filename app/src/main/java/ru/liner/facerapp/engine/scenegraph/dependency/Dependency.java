package ru.liner.facerapp.engine.scenegraph.dependency;


public abstract class Dependency<T> {
    private int version = 0;
    private int lastUpdateVersion = -1;

    public abstract T get();

    
    public abstract void updateSelf(long j);

    public synchronized void invalidate() {
        this.version++;
    }

    public synchronized int getVersion() {
        return this.version;
    }

    public synchronized boolean isInvalidated() {
        return this.version > this.lastUpdateVersion;
    }

    public final synchronized void update(long currentTimeMillis) {
        if (isInvalidated()) {
            updateSelf(currentTimeMillis);
            this.lastUpdateVersion = this.version;
        }
    }
}
