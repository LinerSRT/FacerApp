package ru.liner.facerapp.engine.scenegraph.dependency;

import ru.liner.facerapp.engine.RenderEnvironment;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ScaleDependency extends Dependency<Float>{
    private float size;
    private Mode mode;

    public enum Mode{
        x,
        y
    }

    public ScaleDependency(float size, Mode mode) {
        this.size = size;
        this.mode = mode;
    }

    @Override
    public Float get() {
        switch (mode){
            case x:
                return size / RenderEnvironment.getInstance().getCanvasSizeX();
            case y:
                return size / RenderEnvironment.getInstance().getCanvasSizeY();
            default:
                return 1f;
        }
    }

    @Override
    public void updateSelf(long j) {

    }
}
