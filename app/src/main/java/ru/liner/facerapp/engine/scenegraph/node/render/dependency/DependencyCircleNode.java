package ru.liner.facerapp.engine.scenegraph.node.render.dependency;


import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.CircleNode;

/* loaded from: classes.dex */
public class DependencyCircleNode extends CircleNode {
    private Dependency<Float> radiusDependency = null;

    public DependencyCircleNode(Dependency<Float> radiusDependency) {
        super(1.0f);
        setRadiusDependency(radiusDependency);
    }

    public synchronized void setRadiusDependency(Dependency<Float> dependency) {
        this.radiusDependency = dependency;
        if (this.radiusDependency != null) {
            this.radiusDependency.invalidate();
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public synchronized void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.radiusDependency != null) {
            this.radiusDependency.update(currentTimeMillis);
            setRadius(this.radiusDependency.get().floatValue());
        }
    }
}
