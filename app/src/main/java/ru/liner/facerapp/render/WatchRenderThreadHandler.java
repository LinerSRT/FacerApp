package ru.liner.facerapp.render;

import android.util.Log;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class WatchRenderThreadHandler<R> extends RenderThreadHandler<R> {
    private boolean isInAmbient = false;
    private boolean isHidden = false;
    private boolean isLocked = false;

    @Override 
    public synchronized void onEnterAmbientMode() {
        isInAmbient = true;
        evaluateState();
        runSingleFrame();
    }

    @Override 
    public synchronized void onExitAmbientMode() {
        isInAmbient = false;
        evaluateState();
    }

    @Override 
    public synchronized void onHide() {
        isHidden = true;
        evaluateState();
    }

    @Override 
    public synchronized void onShow() {
        isHidden = false;
        evaluateState();
    }

    protected synchronized void evaluateState() {
        if (isStarted()) {
            if (isInAmbient || isHidden) {
                pause();
            } else {
                resume();
            }
        }
    }

    public synchronized void lockPaused() {
        pause();
        isLocked = true;
        Log.w(WatchRenderThreadHandler.class.getSimpleName(), " ::: RENDERER IS PAUSE LOCKED [" + getId() + "]");
    }

    public synchronized void unlockPaused() {
        isLocked = false;
        Log.w(WatchRenderThreadHandler.class.getSimpleName(), " ::: RENDERER PAUSE UNLOCKED [" + getId() + "]");
        evaluateState();
    }

    @Override 
    public synchronized void resume() {
        if (!isLocked) {
            super.resume();
        } else {
            runSingleFrame();
        }
    }
}