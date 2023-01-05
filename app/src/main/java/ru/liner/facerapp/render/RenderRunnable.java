package ru.liner.facerapp.render;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.UUID;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public abstract class RenderRunnable <R> implements Runnable {
    public static final float UPS = 30f;
    private final Engine<R> engine;
    private final Object renderLock = new Object();
    private boolean shouldStop = false;
    private boolean isStopped = true;
    private long lastUpdateFrameMillis = System.currentTimeMillis();
    private float lastUpdateDeltaSeconds = 0.0f;

    protected abstract R lockRenderTarget();

    protected abstract void unlockRenderTarget(R r);

    public RenderRunnable(@NonNull Engine<R> engine) {
        this.engine = engine;
    }

    public synchronized void stop() {
        shouldStop = true;
        Log.w(RenderRunnable.class.getSimpleName(), " :: RENDER RUNNABLE STOP() CALLED ::");
    }

    public synchronized void resume() {
        shouldStop = false;
        Log.w(RenderRunnable.class.getSimpleName(), " :: RENDER RUNNABLE RESUME() CALLED ::");
    }

    public synchronized boolean shouldStop() {
        return this.shouldStop;
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }

    protected synchronized boolean shouldUpdate() {
        boolean shouldUpdate = false;
        long currentTimeMillis = System.currentTimeMillis();
        float timeDeltaSeconds = ((float) (currentTimeMillis - lastUpdateFrameMillis)) / 1000.0f;
        lastUpdateFrameMillis = currentTimeMillis;
        lastUpdateDeltaSeconds += timeDeltaSeconds;
        if (lastUpdateDeltaSeconds > 0.06666667f)
            lastUpdateDeltaSeconds = 0.06666667f;
        if (lastUpdateDeltaSeconds >= 0.033333335f) {
            lastUpdateDeltaSeconds -= 0.033333335f;
            shouldUpdate = true;
        }
        return shouldUpdate;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Render Thread [" + UUID.randomUUID() + "]");
        synchronized (this) {
            isStopped = false;
        }
        while (!Thread.currentThread().isInterrupted() && !shouldStop()) {
            try {
                runSingleFrame(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        synchronized (this) {
            isStopped = true;
        }
        Log.w(RenderRunnable.class.getSimpleName(), " -> RenderRunnable.run() stopped.");
    }

    public void runSingleFrame(boolean shouldForceUpdate) throws InterruptedException {
        synchronized (renderLock) {
            if (shouldUpdate() || shouldForceUpdate)
                update(System.currentTimeMillis());
            R target = lockRenderTarget();
            if (target != null)
                render(target);
            if (target != null)
                unlockRenderTarget(target);
        }
    }

    protected void update(long currentTimeMillis) {
        synchronized (renderLock) {
            engine.update(currentTimeMillis);
        }
    }

    protected void render(@NonNull R renderTarget){
        synchronized (renderLock) {
            engine.render(renderTarget);
        }
    }
}