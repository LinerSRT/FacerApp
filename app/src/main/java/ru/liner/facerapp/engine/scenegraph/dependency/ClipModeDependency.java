package ru.liner.facerapp.engine.scenegraph.dependency;

import ru.liner.facerapp.engine.RenderEnvironment;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ClipModeDependency<T> extends StateDependency<RenderEnvironment.ClipMode, T> {
    public ClipModeDependency(Dependency<T> validState, Dependency<T> invalidState) {
        super(validState, invalidState);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.StateDependency
    public RenderEnvironment.ClipMode getCurrentMode() {
        RenderEnvironment environment = RenderEnvironment.getInstance();
        if (environment != null) {
            return environment.getClipMode();
        }
        return null;
    }
}