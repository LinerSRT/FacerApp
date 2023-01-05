package ru.liner.facerapp.engine.canvas;

import android.graphics.Canvas;

import ru.liner.facerapp.engine.scenegraph.SceneGraph;

public interface CanvasRenderer {
    void draw(Canvas canvas);

    void shutdown();

    void updateDrawCache(SceneGraph sceneGraph);

}
