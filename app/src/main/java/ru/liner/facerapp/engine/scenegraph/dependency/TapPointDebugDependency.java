package ru.liner.facerapp.engine.scenegraph.dependency;


import androidx.annotation.NonNull;


public class TapPointDebugDependency extends Dependency<Float> {
    private static float pointX = 0.0f;
    private static float pointY = 0.0f;
    private final Mode mode;

    
    public enum Mode {
        X,
        Y
    }

    public TapPointDebugDependency(@NonNull Mode mode) {
        this.mode = mode;
    }

    
    @Override 
    public void updateSelf(long currentTimeMillis) {
    }

    
    @Override 
    public Float get() {
        if (Mode.X.equals(this.mode)) {
            return pointX;
        }
        if (Mode.Y.equals(this.mode)) {
            return pointX;
        }
        return 0f;
    }

    @Override 
    public synchronized boolean isInvalidated() {
        return true;
    }

    public static void setTapPoint(float pointX2, float pointY2) {
        pointX = pointX2;
        pointY = pointY2;
    }
}
