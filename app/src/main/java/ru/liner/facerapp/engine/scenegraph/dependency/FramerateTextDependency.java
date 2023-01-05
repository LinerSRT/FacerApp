package ru.liner.facerapp.engine.scenegraph.dependency;

import java.util.Locale;

import ru.liner.facerapp.engine.FPSTracker;


public class FramerateTextDependency extends Dependency<String> {
    private static FPSTracker fpsTracker = new FPSTracker();
    private float framesPerSecond = 0.0f;

    
    @Override 
    public void updateSelf(long currentTimeMillis) {
        synchronized (FramerateTextDependency.class) {
            this.framesPerSecond = (float) fpsTracker.getFPS();
        }
    }

    @Override 
    public String get() {
        return String.format(Locale.getDefault(), "%.2f", Float.valueOf(this.framesPerSecond));
    }

    @Override 
    public synchronized boolean isInvalidated() {
        return true;
    }

    public static void onFrameRendered() {
        synchronized (FramerateTextDependency.class) {
            fpsTracker.increment();
        }
    }
}
