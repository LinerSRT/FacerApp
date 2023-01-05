package ru.liner.facerapp.wrapper;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import ru.liner.facerapp.engine.FPSTracker;
import ru.liner.facerapp.engine.canvas.CanvasRenderer;
import ru.liner.facerapp.engine.canvas.GLRender;
import ru.liner.facerapp.engine.drawcache.SimpleDrawCacheStrategy;
import ru.liner.facerapp.engine.scenegraph.SceneGraph;
import ru.liner.facerapp.engine.scenegraph.dependency.FramerateTextDependency;
import ru.liner.facerapp.render.Engine;
import ru.liner.facerapp.render.RenderEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class FacerRenderer extends RenderEngine implements Engine<Canvas> {
    protected FPSTracker fpsTracker;
    protected final CanvasRenderer canvasRenderer;
    protected SceneGraph sceneGraph;

    public FacerRenderer(Context context) {
        this(context, null);
    }

    public FacerRenderer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FacerRenderer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.fpsTracker = new FPSTracker();
        this.canvasRenderer = new GLRender(new SimpleDrawCacheStrategy());
    }

    @Override
    public void render(Canvas render) {
        canvasRenderer.draw(render);
        FramerateTextDependency.onFrameRendered();
        fpsTracker.increment();
    }

    @Override
    public void handleClickEvent(float x, float y) {

    }

    @Override
    public void sizeChanged(float width, float height) {

    }

    @Override
    public void update(long currentTimeMillis) {
        if (sceneGraph != null)
            sceneGraph.update(currentTimeMillis);
        canvasRenderer.updateDrawCache(sceneGraph);
    }

    @Override
    public Engine<Canvas> getEngine(Context context) {
        return this;
    }

    public SceneGraph getSceneGraph() {
        return sceneGraph;
    }
}
