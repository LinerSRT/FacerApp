package ru.liner.facerapp.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import ru.liner.facerapp.render.Engine;
import ru.liner.facerapp.render.RenderEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class OpenGLRenderer extends RenderEngine implements Engine<Canvas>{

    public OpenGLRenderer(Context context) {
        this(context, null);
    }

    public OpenGLRenderer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OpenGLRenderer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Engine<Canvas> getEngine(Context context) {
        return this;
    }

    @Override
    public void render(Canvas render) {

    }

    @Override
    public void handleClickEvent(float x, float y) {

    }

    @Override
    public void sizeChanged(float width, float height) {

    }

    @Override
    public void update(long currentTimeMillis) {

    }
}
