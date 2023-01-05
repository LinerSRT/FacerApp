package ru.liner.facerapp.engine.scenegraph.node.render.dependency;


import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.Shape;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.PolygonNode;

/* loaded from: classes.dex */
public class DependencyPolygonNode extends PolygonNode {
    private Dependency<Float> radiusDependency = null;

    public DependencyPolygonNode(@NonNull Shape shape, Dependency<Float> radiusDependency) {
        super(shape, 1.0f);
        setRadiusDependency(radiusDependency);
    }

    public synchronized void setRadiusDependency(Dependency<Float> radiusDependency) {
        this.radiusDependency = radiusDependency;
        if (radiusDependency != null) {
            radiusDependency.invalidate();
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
