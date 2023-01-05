package ru.liner.facerapp.engine.scenegraph.node.render.dependency;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorNode;
import ru.liner.facerapp.engine.scenegraph.node.render.DrawableNode;

@Deprecated
/* loaded from: classes.dex */
public class DependencyDrawableNode extends DrawableNode {
    protected final int DEPENDENCY_DRAWABLE = 0;
    protected final int DEPENDENCY_WIDTH = 1;
    protected final int DEPENDENCY_HEIGHT = 2;
    protected final int DEPENDENCY_COLOR_FILTER = 3;
    protected final int DEPENDENCY_ALPHA = 4;

    public DependencyDrawableNode(Dependency<Drawable> drawableDependency, Dependency<Float> widthDependency, Dependency<Float> heightDependency) {
        super(null, 0.0f, 0.0f);
        setDrawableDependency(drawableDependency);
        setWidthDependency(widthDependency);
        setHeightDependency(heightDependency);
    }

    public DependencyDrawableNode(Dependency<Drawable> drawableDependency, Dependency<Float> widthDependency, Dependency<Float> heightDependency, Alignment alignment) {
        super(null, 0.0f, 0.0f, alignment);
        setDrawableDependency(drawableDependency);
        setWidthDependency(widthDependency);
        setHeightDependency(heightDependency);
    }

    public synchronized void setDrawableDependency(Dependency<Drawable> dependency) {
        setDependency(0, dependency);
    }

    protected synchronized Dependency<Drawable> getDrawableDependency() {
        return getDependency(0);
    }

    public synchronized void setWidthDependency(Dependency<Float> dependency) {
        setDependency(1, dependency);
    }

    protected synchronized Dependency<Float> getWidthDependency() {
        return getDependency(1);
    }

    public synchronized void setHeightDependency(Dependency<Float> dependency) {
        setDependency(2, dependency);
    }

    protected synchronized Dependency<Float> getHeightDependency() {
        return getDependency(2);
    }

    public synchronized void setColorFilterDependency(Dependency<ColorFilter> dependency) {
        setDependency(3, dependency);
    }

    protected synchronized Dependency<ColorFilter> getColorFilterDependency() {
        return getDependency(3);
    }

    public synchronized void setAlphaDependency(Dependency<Float> dependency) {
        setDependency(4, dependency);
    }

    protected synchronized Dependency<Float> getAlphaDependency() {
        return getDependency(4);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public synchronized void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        Dependency<Drawable> drawableDependency = getDrawableDependency();
        if (drawableDependency != null) {
            setDrawable(drawableDependency.get());
        }
        Dependency<Float> widthDependency = getWidthDependency();
        if (widthDependency != null) {
            setWidth(widthDependency.get().floatValue());
        }
        Dependency<Float> heightDependency = getHeightDependency();
        if (heightDependency != null) {
            setHeight(heightDependency.get().floatValue());
        }
        Dependency<ColorFilter> colorFilterDependency = getColorFilterDependency();
        if (colorFilterDependency != null) {
            setColorFilter(colorFilterDependency.get());
        }
        Dependency<Float> alphaDependency = getAlphaDependency();
        if (alphaDependency != null) {
            setAlpha(ColorNode.toAlphaInt((double) alphaDependency.get().floatValue()));
        }
    }
}
