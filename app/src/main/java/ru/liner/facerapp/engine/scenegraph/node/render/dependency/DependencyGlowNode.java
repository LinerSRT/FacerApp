package ru.liner.facerapp.engine.scenegraph.node.render.dependency;


import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.GlowNode;

/* loaded from: classes.dex */
public class DependencyGlowNode extends GlowNode {
    private Dependency<Integer> colorDependency = null;
    private Dependency<Integer> sizeDependency = null;

    public DependencyGlowNode(Dependency<Integer> colorDependency, Dependency<Integer> sizeDependency) {
        super(-1, 0);
        setColorDependency(colorDependency);
        setSizeDependency(sizeDependency);
    }

    public synchronized void setColorDependency(Dependency<Integer> dependency) {
        this.colorDependency = dependency;
        if (this.colorDependency != null) {
            this.colorDependency.invalidate();
        }
    }

    public synchronized void setSizeDependency(Dependency<Integer> dependency) {
        this.sizeDependency = dependency;
        if (this.sizeDependency != null) {
            this.sizeDependency.invalidate();
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.colorDependency != null) {
            this.colorDependency.update(currentTimeMillis);
            setColor(this.colorDependency.get().intValue());
        }
        if (this.sizeDependency != null) {
            this.sizeDependency.update(currentTimeMillis);
            setSize(this.sizeDependency.get().intValue());
        }
    }
}
