package ru.liner.facerapp.engine.state;

import android.util.Log;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import ru.liner.facerapp.engine.decoder.source.DataSource;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public abstract class PreservedState<T> implements State<T> {
    private T state = null;
    private final ReentrantReadWriteLock stateLock = new ReentrantReadWriteLock();

    protected abstract T loadState();

    protected abstract boolean saveState(T t);

    @Override // com.jeremysteckling.facerrel.lib.sync.local.provenance.state.State
    public T get() {
        T localState = null;
        try {
            try {
                if (this.stateLock.readLock().tryLock(300L, TimeUnit.MILLISECONDS)) {
                    try {
                        localState = this.state;
                    } finally {
                        try {
                            this.stateLock.readLock().unlock();
                        } catch (IllegalMonitorStateException e) {
                            Log.w(PreservedState.class.getSimpleName(), "Failed to release readlock (inner), lock was never acquired; aborting.", e);
                        }
                    }
                }
            } catch (IllegalMonitorStateException e2) {
                Log.e(PreservedState.class.getSimpleName(), "Could not release readLock (outer), lock was never acquired; aborting.", e2);
            }
        } catch (InterruptedException e3) {
            Log.d(PreservedState.class.getSimpleName(), "Unable to acquire readLock; aborting.", e3);
        } catch (Exception e4) {
            Log.e(DataSource.class.getSimpleName(), "DataSource.getState() could not be completed due to exception; aborting.", e4);
        }
        if (localState == null) {
            localState = loadState();
            this.stateLock.writeLock().lock();
            try {
                this.state = localState;
            } finally {
                this.stateLock.writeLock().unlock();
            }
        }
        return localState;
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.jeremysteckling.facerrel.lib.sync.local.provenance.state.State
    public void set(T state) {
        this.stateLock.writeLock().lock();
        try {
            this.state = state;
            this.stateLock.writeLock().unlock();
            saveState(state);
        } catch (Throwable th) {
            this.stateLock.writeLock().unlock();
            throw th;
        }
    }
}