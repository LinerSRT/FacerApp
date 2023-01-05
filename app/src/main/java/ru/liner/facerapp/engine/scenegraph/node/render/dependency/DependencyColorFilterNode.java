package ru.liner.facerapp.engine.scenegraph.node.render.dependency;

import android.graphics.PorterDuff;

import androidx.annotation.ColorInt;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorFilterNode;

/* loaded from: classes.dex */
public class DependencyColorFilterNode extends ColorFilterNode {
    private Dependency<Integer> colorDependency = null;

    public DependencyColorFilterNode(Dependency<Integer> colorDependency) {
        super(-1);
        setColorDependency(colorDependency);
    }

    public DependencyColorFilterNode(@ColorInt int color, PorterDuff.Mode filterMode) {
        super(-1, filterMode);
    }

    public synchronized void setColorDependency(Dependency<Integer> dependency) {
        this.colorDependency = dependency;
        if (this.colorDependency != null) {
            this.colorDependency.invalidate();
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.colorDependency != null) {
            this.colorDependency.update(currentTimeMillis);
            setColor(this.colorDependency.get().intValue());
        }
    }
}
