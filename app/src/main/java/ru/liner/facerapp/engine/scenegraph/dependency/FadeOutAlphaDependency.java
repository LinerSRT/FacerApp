package ru.liner.facerapp.engine.scenegraph.dependency;

import android.view.animation.AccelerateInterpolator;


public class FadeOutAlphaDependency extends Dependency<Float> {
    private static final String TAG = FadeOutAlphaDependency.class.getSimpleName();
    private float currentValue;
    private long duration;
    private boolean hasStarted = false;
    private boolean isDone = false;
    private long startTime;
    private float startValue;

    public FadeOutAlphaDependency(float startValue, long duration) {
        this.startValue = startValue;
        this.duration = duration;
    }

    
    @Override 
    public void updateSelf(long currentTimeMillis) {
        if (!this.hasStarted) {
            start();
        }
        long elapsedTime = currentTimeMillis - this.startTime;
        AccelerateInterpolator interpolator = new AccelerateInterpolator(2.0f);
        if (this.duration > 0) {
            this.currentValue = 1.0f - interpolator.getInterpolation(((float) elapsedTime) / ((float) this.duration));
        } else {
            this.currentValue = 0.0f;
            this.isDone = true;
        }
        if (elapsedTime >= this.duration) {
            this.currentValue = 0.0f;
            this.isDone = true;
        }
    }

    private void start() {
        this.currentValue = this.startValue;
        this.startTime = System.currentTimeMillis();
        this.hasStarted = true;
    }

    @Override 
    public synchronized boolean isInvalidated() {
        return !this.isDone;
    }

    
    @Override 
    public Float get() {
        return Float.valueOf(this.currentValue);
    }
}
