package ru.liner.facerapp.engine.scenegraph.node.render.dependency;


import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorNode;

/* loaded from: classes.dex */
public class DependencyColorNode extends ColorNode {
    private Dependency<Float> alphaDependency;
    private Dependency<Integer> colorDependency;

    public DependencyColorNode(Dependency<Integer> colorDependency) {
        this(colorDependency, null);
    }

    public DependencyColorNode(Dependency<Integer> colorDependency, Dependency<Float> alphaDependency) {
        super(-1, 0);
        this.colorDependency = null;
        this.alphaDependency = null;
        setColorDependency(colorDependency);
        setAlphaDependency(alphaDependency);
    }

    public synchronized void setColorDependency(Dependency<Integer> dependency) {
        this.colorDependency = dependency;
        if (this.colorDependency != null) {
            this.colorDependency.invalidate();
        }
    }

    public synchronized void setAlphaDependency(Dependency<Float> dependency) {
        this.alphaDependency = dependency;
        if (this.alphaDependency != null) {
            this.alphaDependency.invalidate();
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public synchronized void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.colorDependency != null) {
            this.colorDependency.update(currentTimeMillis);
            setColor(this.colorDependency.get().intValue());
        }
        if (this.alphaDependency != null) {
            this.alphaDependency.update(currentTimeMillis);
            setAlpha(toAlphaInt((double) this.alphaDependency.get().floatValue()));
        }
    }
}
