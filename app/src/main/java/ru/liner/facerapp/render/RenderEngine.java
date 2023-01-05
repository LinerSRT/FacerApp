package ru.liner.facerapp.render;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.UUID;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public abstract class RenderEngine extends SurfaceView implements SurfaceHolder.Callback{
    private final UUID uuid = UUID.randomUUID();
    private SurfaceHolder surfaceHolder;
    private final Engine<Canvas> canvasEngine;
    protected final RenderThreadHandler<Canvas> threadHandler;

    public RenderEngine(Context context) {
        this(context, null);
    }

    public RenderEngine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RenderEngine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        threadHandler = new WatchRenderThreadHandler<>();
        canvasEngine = getEngine(context);
    }

    public abstract Engine<Canvas> getEngine(Context context);

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        if (threadHandler != null) {
            if (!threadHandler.isStarted()) {
                threadHandler.start(new RenderEngineRunnable(canvasEngine));
            } else {
                threadHandler.resume();
            }
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        canvasEngine.sizeChanged(i1, i2);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.w(RenderEngine.class.getSimpleName(), " ::: SURFACE DESTROYED FOR ENGINE [" + getID() + "] :::");
        if (threadHandler != null)
            threadHandler.terminate();
        this.surfaceHolder = null;
    }

    public String getID() {
        return uuid.toString();
    }

    private class RenderEngineRunnable extends RenderRunnable<Canvas>{
        @Override
        protected Canvas lockRenderTarget() {
            try {
                if (surfaceHolder != null)
                    return surfaceHolder.lockCanvas(null);
                return null;
            } catch (Exception e) {
                Log.e(RenderEngineRunnable.class.getSimpleName(), "Unable to lock the canvas due to exception.", e);
                return null;
            }

        }

        @Override
        protected void unlockRenderTarget(Canvas canvas) {
            try {
                if (surfaceHolder != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            } catch (Exception e) {
                Log.e(RenderEngineRunnable.class.getSimpleName(), "Unable to unlock and post the canvas due to exception.", e);
            }
        }

        public RenderEngineRunnable(@NonNull Engine<Canvas> engine) {
            super(engine);
        }
    }
}
