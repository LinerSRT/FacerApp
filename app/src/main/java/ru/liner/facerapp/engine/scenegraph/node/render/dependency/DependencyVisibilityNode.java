package ru.liner.facerapp.engine.scenegraph.node.render.dependency;


import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.VisibilityNode;

/* loaded from: classes.dex */
public class DependencyVisibilityNode extends VisibilityNode {
    private Dependency<Boolean> visibilityDependency = null;

    public DependencyVisibilityNode() {
    }

    public DependencyVisibilityNode(Dependency<Boolean> visibilityDependency) {
        setVisibilityDependency(visibilityDependency);
    }

    public synchronized void setVisibilityDependency(Dependency<Boolean> dependency) {
        this.visibilityDependency = dependency;
        if (this.visibilityDependency != null) {
            this.visibilityDependency.invalidate();
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.visibilityDependency != null) {
            this.visibilityDependency.update(currentTimeMillis);
            setVisible(this.visibilityDependency.get().booleanValue());
        }
    }
}
