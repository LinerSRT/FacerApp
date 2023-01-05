package ru.liner.facerapp.render;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public abstract class RenderThreadHandler<R> implements ThreadHandler<R>{
    private RenderRunnable<R> runnable;
    private final UUID uuid = UUID.randomUUID();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public String getId() {
        return uuid.toString();
    }

    public RenderRunnable<R> getRunnable() {
        return runnable;
    }

    public synchronized boolean isStarted() {
        return runnable != null;
    }

    @Override
    public synchronized void pause() {
        if (runnable != null && !runnable.isStopped()) {
            runnable.stop();
            Log.w(RenderThreadHandler.class.getSimpleName(), " ::: PAUSED RENDER THREAD [" + uuid.toString() + "] :::");
        }

    }

    @Override
    public synchronized void resume() {
        if (runnable != null && runnable.isStopped()) {
            runnable.resume();
            try {
                executor.execute(runnable);
            } catch (RejectedExecutionException e) {
                Log.e(RenderThreadHandler.class.getSimpleName(), " -> Render Thread [" + uuid.toString() + "] threw an exception while attempting to start the render task.", e);
            }
            Log.w(RenderThreadHandler.class.getSimpleName(), " ::: RESUMED RENDER THREAD [" + uuid.toString() + "] :::");
        }

    }

    @Override
    public synchronized void shutdown() {
        if (runnable != null)
            runnable.stop();
        executor.shutdown();
        Log.w(RenderThreadHandler.class.getSimpleName(), " ::: SHUTDOWN RENDER THREAD [" + uuid.toString() + "] :::");

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public synchronized void terminate() {
        if (runnable != null)
            runnable.stop();
        try {
            executor.shutdownNow();
            executor.awaitTermination(1000L, TimeUnit.MILLISECONDS);
            Log.w(RenderThreadHandler.class.getSimpleName(), " ::: TERMINATED RENDER THREAD [" + uuid.toString() + "] :::");
        } catch (InterruptedException e) {
            Log.w(RenderThreadHandler.class.getSimpleName(), " ::: INTERRUPTED TERMINATING RENDER THREAD [" + uuid.toString() + "] :::");
        }

    }

    @Override
    public synchronized void start(@NonNull RenderRunnable<R> runnable) {
        if (this.runnable == null) {
            this.runnable = runnable;
            executor.execute(runnable);
            Log.w(RenderThreadHandler.class.getSimpleName(), " ::: STARTED RENDER THREAD [" + uuid.toString() + "] :::");
        } else {
            Log.e(RenderThreadHandler.class.getSimpleName(), "Could not start RenderThreadHandler [" + uuid.toString() + "], handler was already started.");
        }
    }
    public synchronized void runSingleFrame() {
        try {
            runnable.runSingleFrame(isStarted());
            Log.w(RenderThreadHandler.class.getSimpleName(), " ::: RENDER THREAD [" + uuid.toString() + "] RENDERED SINGLE FRAME :::");
        } catch (InterruptedException e) {
            Log.w(RenderThreadHandler.class.getSimpleName(), " -> Render Thread [" + uuid.toString() + "] threw an exception while trying to render a single frame.", e);
        }
    }


    public void onEnterAmbientMode() {
    }

    public void onExitAmbientMode() {
    }

    public void onHide() {
    }

    public void onShow() {
    }

}
