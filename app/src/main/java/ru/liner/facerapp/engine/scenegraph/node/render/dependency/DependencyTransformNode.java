package ru.liner.facerapp.engine.scenegraph.node.render.dependency;


import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.TransformNode;

/* loaded from: classes.dex */
public class DependencyTransformNode extends TransformNode {
    private Dependency<Float> xDependency = null;
    private Dependency<Float> yDependency = null;
    private Dependency<Float> rotDependency = null;
    private Dependency<Float> scaleXDependency = null;
    private Dependency<Float> scaleYDependency = null;

    public DependencyTransformNode() {
    }

    public DependencyTransformNode(int mask) {
        super(mask);
    }

    public DependencyTransformNode(Dependency<Float> xDependency, Dependency<Float> yDependency) {
        setXDependency(xDependency);
        setYDependency(yDependency);
    }

    public DependencyTransformNode(Dependency<Float> xDependency, Dependency<Float> yDependency, int mask) {
        super(mask);
        setXDependency(xDependency);
        setYDependency(yDependency);
    }

    public synchronized void setXDependency(Dependency<Float> dependency) {
        this.xDependency = dependency;
        if (this.xDependency != null) {
            this.xDependency.invalidate();
        }
    }

    public synchronized void setYDependency(Dependency<Float> dependency) {
        this.yDependency = dependency;
        if (this.yDependency != null) {
            this.yDependency.invalidate();
        }
    }

    public synchronized void setRotDependency(Dependency<Float> dependency) {
        this.rotDependency = dependency;
        if (this.rotDependency != null) {
            this.rotDependency.invalidate();
        }
    }

    public synchronized void setScaleXDependency(Dependency<Float> dependency) {
        this.scaleXDependency = dependency;
        if (this.scaleXDependency != null) {
            this.scaleXDependency.invalidate();
        }
    }

    public synchronized void setScaleYDependency(Dependency<Float> dependency) {
        this.scaleYDependency = dependency;
        if (this.scaleYDependency != null) {
            this.scaleYDependency.invalidate();
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public synchronized void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.xDependency != null) {
            this.xDependency.update(currentTimeMillis);
            setPosX(this.xDependency.get().floatValue());
        }
        if (this.yDependency != null) {
            this.yDependency.update(currentTimeMillis);
            setPosY(this.yDependency.get().floatValue());
        }
        if (this.rotDependency != null) {
            this.rotDependency.update(currentTimeMillis);
            setRotationDeg(this.rotDependency.get().floatValue());
        }
        if (this.scaleXDependency != null) {
            this.scaleXDependency.update(currentTimeMillis);
            setScaleX(this.scaleXDependency.get().floatValue());
        }
        if (this.scaleYDependency != null) {
            this.scaleYDependency.update(currentTimeMillis);
            setScaleY(this.scaleYDependency.get().floatValue());
        }
    }
}
