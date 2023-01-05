package ru.liner.facerapp.engine.scenegraph.dependency;

import ru.liner.facerapp.engine.RenderEnvironment;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class RenderModeDependency <T> extends StateDependency<RenderEnvironment.RenderMode, T> {
    public RenderModeDependency(Dependency<T> validState, Dependency<T> invalidState) {
        super(validState, invalidState);
    }
    @Override
    public RenderEnvironment.RenderMode getCurrentMode() {
        RenderEnvironment environment = RenderEnvironment.getInstance();
        if (environment != null) {
            return environment.getRenderMode();
        }
        return null;
    }
}