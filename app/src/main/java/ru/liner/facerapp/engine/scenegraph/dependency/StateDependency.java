package ru.liner.facerapp.engine.scenegraph.dependency;

import java.util.ArrayList;
import java.util.Iterator;


public abstract class StateDependency<M, T> extends Dependency<T> {
    private Dependency<T> invalidState;
    private Dependency<T> validState;
    private ArrayList<M> validModes = new ArrayList<>();
    private ForceState forceState = ForceState.DEFAULT;

    
    
    public enum ForceState {
        DISABLED,
        ENABLED,
        DEFAULT
    }

    protected abstract M getCurrentMode();

    public StateDependency(Dependency<T> validState, Dependency<T> invalidState) {
        this.validState = null;
        this.invalidState = null;
        this.validState = validState;
        this.invalidState = invalidState;
    }

    public void setAlwaysDisabled() {
        this.validModes.clear();
        this.forceState = ForceState.DISABLED;
    }

    public void setAlwaysEnabled() {
        setAlwaysDisabled();
        this.forceState = ForceState.ENABLED;
    }

    public void addVisibleMode(M mode) {
        if (mode != null && !this.validModes.contains(mode)) {
            this.validModes.add(mode);
        }
        this.forceState = ForceState.DEFAULT;
    }

    public void hideInVisibleMode(M mode) {
        if (mode != null) {
            this.validModes.remove(mode);
        }
        this.forceState = ForceState.DEFAULT;
    }

    @Override 
    public synchronized void updateSelf(long currentTimeMillis) {
        if (this.validState != null) {
            this.validState.update(currentTimeMillis);
        }
        if (this.invalidState != null) {
            this.invalidState.update(currentTimeMillis);
        }
    }

    @Override 
    public synchronized T get() {
        T t = null;
        synchronized (this) {
            if (isValid()) {
                if (this.validState != null) {
                    t = this.validState.get();
                }
            } else if (this.invalidState != null) {
                t = this.invalidState.get();
            }
        }
        return t;
    }

    protected boolean isValid() {
        if (ForceState.ENABLED.equals(this.forceState)) {
            return true;
        }
        if (ForceState.DISABLED.equals(this.forceState)) {
            return false;
        }
        if (!this.validModes.isEmpty()) {
            M currentMode = getCurrentMode();
            Iterator<M> it = this.validModes.iterator();
            while (it.hasNext()) {
                if (it.next().equals(currentMode)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override 
    public synchronized boolean isInvalidated() {
        boolean isInvalid;
        isInvalid = false;
        if (this.validState != null) {
            isInvalid = false | this.validState.isInvalidated();
        }
        if (this.invalidState != null) {
            isInvalid |= this.invalidState.isInvalidated();
        }
        return isInvalid;
    }
}
