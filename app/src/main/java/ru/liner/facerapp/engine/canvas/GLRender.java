package ru.liner.facerapp.engine.canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.drawcache.DrawCache;
import ru.liner.facerapp.engine.drawcache.DrawCacheStrategy;
import ru.liner.facerapp.engine.scenegraph.SceneGraph;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class GLRender implements CanvasRenderer {
    private final DrawCacheStrategy<CachedRenderable> cacheStrategy;
    private DrawCache<CachedRenderable> drawCache;
    private final Paint paint = new Paint();

    public GLRender(@NonNull DrawCacheStrategy<CachedRenderable> cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }


    @Override
    public void draw(Canvas canvas) {
        DrawCache<CachedRenderable> localCache;
        synchronized (this) {
            localCache = drawCache;
        }
        if (canvas != null && localCache != null) {
            List<CachedRenderable> renderQueue = localCache.getRenderQueue();
            if (renderQueue != null && !renderQueue.isEmpty()) {
                canvas.drawColor(Color.BLACK);
                paint.reset();
                for (int i = 0; i < renderQueue.size(); i++) {
                    CachedRenderable renderable = renderQueue.get(i);
                    renderable.render(canvas, paint);
                }
            }
        }
    }


    @Override
    public void shutdown() {
        this.cacheStrategy.flushCache();
        this.drawCache = null;
    }

    @Override
    public void updateDrawCache(SceneGraph sceneGraph) {
        if (sceneGraph == null) {
            drawCache = null;
            return;
        }
        DrawCache<CachedRenderable> generatedCache = cacheStrategy.generate(sceneGraph);
        synchronized (this) {
            drawCache = generatedCache;
        }

    }
}
