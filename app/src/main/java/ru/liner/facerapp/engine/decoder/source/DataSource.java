package ru.liner.facerapp.engine.decoder.source;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import ru.liner.facerapp.engine.async.Executors;
import ru.liner.facerapp.engine.async.Operation;
import ru.liner.facerapp.engine.async.OperationTask;
import ru.liner.facerapp.engine.state.AlwaysUpdateController;
import ru.liner.facerapp.engine.state.IUpdateController;
import ru.liner.facerapp.engine.state.PreservedState;
import ru.liner.facerapp.engine.state.QuadraticUpdateController;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public abstract class DataSource<T> {
    private final ReentrantReadWriteLock listenerLock;
    private final ArrayList<OnUpdateCompleteListener<T>> listeners;
    private final Options options;
    private final PreservedState<T> state;
    private final ReentrantReadWriteLock stateLock;
    private final IUpdateController updateController;

    /* loaded from: classes2.dex */
    public interface OnUpdateCompleteListener<T> {
        void onUpdateComplete(T t, boolean z);
    }

    protected abstract T update(boolean z);

    public DataSource(@NonNull PreservedState<T> state) {
        this(state, Options.DEFAULT_OPTS);
    }

    public DataSource(@NonNull PreservedState<T> state, @NonNull Options options) {
        this.stateLock = new ReentrantReadWriteLock();
        this.listenerLock = new ReentrantReadWriteLock();
        this.listeners = new ArrayList<>();
        this.state = state;
        this.options = options;
        switch (options.updateRetryMode) {
            case ALWAYS:
                this.updateController = new AlwaysUpdateController();
                return;
            case QUADRATIC:
                this.updateController = new QuadraticUpdateController(options.retryTimeUnit, options.retryInterval, options.retryCount);
                return;
            default:
                this.updateController = new AlwaysUpdateController();
                return;
        }
    }

    public T getState() {
        T state = null;
        try {
            try {
                if (this.stateLock.readLock().tryLock(300L, TimeUnit.MILLISECONDS)) {
                    try {
                        state = this.state.get();
                    } finally {
                        try {
                            this.stateLock.readLock().unlock();
                        } catch (IllegalMonitorStateException e) {
                            Log.w(DataSource.class.getSimpleName(), "Failed to release readLock (inner), lock was never acquired; aborting.", e);
                        }
                    }
                }
            } catch (IllegalMonitorStateException e2) {
                Log.e(DataSource.class.getSimpleName(), "Could not release readLock (outer), lock was never acquired; aborting.", e2);
            }
        } catch (InterruptedException e3) {
            Log.d(DataSource.class.getSimpleName(), "Could not acquire readLock for state; aborting.", e3);
        } catch (Exception e4) {
            Log.e(DataSource.class.getSimpleName(), "DataSource.getState() could not be completed due to exception; aborting.", e4);
        }
        return state;
    }

    public void overrideState(T state) {
        this.stateLock.writeLock().lock();
        try {
            this.state.set(state);
        } finally {
            this.stateLock.writeLock().unlock();
        }
    }

    public void addListener(OnUpdateCompleteListener<T> listener) {
        this.listenerLock.writeLock().lock();
        try {
            if (!this.listeners.contains(listener)) {
                this.listeners.add(listener);
            }
        } finally {
            this.listenerLock.writeLock().unlock();
        }
    }

    public boolean removeListener(OnUpdateCompleteListener<T> listener) {
        this.listenerLock.writeLock().lock();
        try {
            boolean wasRemoved = this.listeners.remove(listener);
            return wasRemoved;
        } finally {
            this.listenerLock.writeLock().unlock();
        }
    }

    public final T updateState() {
        return updateState(false);
    }

    /* JADX WARN: Finally extract failed */
    public final T updateState(boolean shouldForce) {
        T oldState = getState();
        T updatedState = null;
        if (canUpdate(shouldForce)) {
            updatedState = update(shouldForce);
        }
        if (this.options == null || this.options.updateMode != Options.UpdateMode.IGNORE_NULL || updatedState != null) {
            this.stateLock.writeLock().lock();
            try {
                this.state.set(updatedState);
                this.stateLock.writeLock().unlock();
                this.updateController.onUpdateSuccess();
                if (updatedState != oldState || shouldForce) {
                    this.listenerLock.readLock().lock();
                    try {
                        if (this.listeners.size() > 0) {
                            Iterator<OnUpdateCompleteListener<T>> it = this.listeners.iterator();
                            while (it.hasNext()) {
                                OnUpdateCompleteListener<T> listener = it.next();
                                listener.onUpdateComplete(updatedState, shouldForce);
                            }
                        }
                    } finally {
                        this.listenerLock.readLock().unlock();
                    }
                }
            } catch (Throwable th) {
                this.stateLock.writeLock().unlock();
                throw th;
            }
        } else {
            this.updateController.onUpdateFailed();
        }
        return getState();
    }

    public final void updateAsync() {
        updateAsync(false);
    }

    public void updateAsync(final boolean shouldForce) {
        OperationTask<Void, T> task = new OperationTask<>(new Operation<Void, T>() { // from class: com.jeremysteckling.facerrel.lib.sync.local.provenance.DataSource.1
            public T execute(Void input) throws Exception {
                return (T) DataSource.this.updateState(shouldForce);
            }
        });
        task.executeOnExecutor(Executors.getSharedPoolExecutor(), new Void[0]);
    }

    protected boolean canUpdate(boolean shouldForce) {
        return shouldForce || this.updateController.canUpdate();
    }

    /* loaded from: classes2.dex */
    public static class Options {
        public static final Options DEFAULT_OPTS = new Options(UpdateMode.ALLOW_NULL, UpdateRetryMode.ALWAYS, 0, TimeUnit.MINUTES, 1);
        protected final long retryCount;
        protected final long retryInterval;
        protected final TimeUnit retryTimeUnit;
        protected final UpdateMode updateMode;
        protected final UpdateRetryMode updateRetryMode;

        /* loaded from: classes2.dex */
        public enum UpdateMode {
            IGNORE_NULL,
            ALLOW_NULL
        }

        /* loaded from: classes2.dex */
        public enum UpdateRetryMode {
            ALWAYS,
            QUADRATIC
        }

        private Options(@NonNull UpdateMode updateMode, @NonNull UpdateRetryMode updateRetryMode, long retryCount, @NonNull TimeUnit retryTimeUnit, long retryInterval) {
            this.updateMode = updateMode;
            this.updateRetryMode = updateRetryMode;
            this.retryCount = retryCount;
            this.retryTimeUnit = retryTimeUnit;
            this.retryInterval = retryInterval;
        }

        /* loaded from: classes2.dex */
        public static class Builder {
            private UpdateMode updateMode = Options.DEFAULT_OPTS.updateMode;
            private UpdateRetryMode updateRetryMode = Options.DEFAULT_OPTS.updateRetryMode;
            private long retryCount = Options.DEFAULT_OPTS.retryCount;
            private TimeUnit retryTimeUnit = Options.DEFAULT_OPTS.retryTimeUnit;
            private long retryInterval = Options.DEFAULT_OPTS.retryInterval;

            public Builder setUpdateMode(@NonNull UpdateMode updateMode) {
                this.updateMode = updateMode;
                return this;
            }

            public Builder setUpdateRetryMode(@NonNull UpdateRetryMode updateRetryMode) {
                this.updateRetryMode = updateRetryMode;
                return this;
            }

            public Builder setRetryCount(long retryCount) {
                this.retryCount = retryCount;
                return this;
            }

            public Builder setRetryInterval(@NonNull TimeUnit timeUnit, long interval) {
                this.retryTimeUnit = timeUnit;
                this.retryInterval = interval;
                return this;
            }

            public Options build() {
                return new Options(this.updateMode, this.updateRetryMode, this.retryCount, this.retryTimeUnit, this.retryInterval);
            }
        }
    }
}