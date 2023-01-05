package ru.liner.facerapp.engine.scenegraph.node.render.dependency;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.node.render.RectNode;

/* loaded from: classes.dex */
public class DependencyRectNode extends RectNode {
    private Dependency<Float> widthDependency = null;
    private Dependency<Float> heightDependency = null;

    public DependencyRectNode(Dependency<Float> widthDependency, Dependency<Float> heightDependency) {
        super(0.0f, 0.0f);
        setWidthDependency(widthDependency);
        setHeightDependency(heightDependency);
    }

    public DependencyRectNode(Dependency<Float> widthDependency, Dependency<Float> heightDependency, Alignment alignment) {
        super(0.0f, 0.0f, alignment);
        setWidthDependency(widthDependency);
        setHeightDependency(heightDependency);
    }

    public synchronized void setWidthDependency(Dependency<Float> dependency) {
        this.widthDependency = dependency;
        if (this.widthDependency != null) {
            this.widthDependency.invalidate();
        }
    }

    public synchronized void setHeightDependency(Dependency<Float> dependency) {
        this.heightDependency = dependency;
        if (this.heightDependency != null) {
            this.heightDependency.invalidate();
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public synchronized void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.widthDependency != null) {
            this.widthDependency.update(currentTimeMillis);
            setWidth(this.widthDependency.get().floatValue());
        }
        if (this.heightDependency != null) {
            this.heightDependency.update(currentTimeMillis);
            setHeight(this.heightDependency.get().floatValue());
        }
    }
}
