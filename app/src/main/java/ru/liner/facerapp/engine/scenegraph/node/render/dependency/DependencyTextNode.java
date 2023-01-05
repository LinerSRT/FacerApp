package ru.liner.facerapp.engine.scenegraph.node.render.dependency;

import android.graphics.Paint;
import android.graphics.Typeface;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.TextNode;

/* loaded from: classes.dex */
public class DependencyTextNode extends TextNode {
    private static final float DEFAULT_FONT_SIZE = 16.0f;
    protected final int DEPENDENCY_TEXT = 0;
    protected final int DEPENDENCY_TYPEFACE = 1;
    protected final int DEPENDENCY_SIZE = 2;

    public DependencyTextNode(Dependency<String> textDependency, Dependency<Typeface> typefaceDependency, Dependency<Float> sizeDependency, Paint.Align alignment) {
        super(null, null, DEFAULT_FONT_SIZE, alignment);
        setTextDependency(textDependency);
        setTypefaceDependency(typefaceDependency);
        setSizeDependency(sizeDependency);
    }

    public synchronized void setTextDependency(Dependency<String> textDependency) {
        setDependency(0, textDependency);
    }

    protected synchronized Dependency<String> getTextDependency() {
        return getDependency(0);
    }

    public synchronized void setTypefaceDependency(Dependency<Typeface> dependency) {
        setDependency(1, dependency);
    }

    protected synchronized Dependency<Typeface> getTypefaceDependency() {
        return getDependency(1);
    }

    public synchronized void setSizeDependency(Dependency<Float> sizeDependency) {
        setDependency(2, sizeDependency);
    }

    protected synchronized Dependency<Float> getSizeDependency() {
        return getDependency(2);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.DependencySceneNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.SceneNode
    public synchronized void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        Dependency<String> textDependency = getTextDependency();
        if (textDependency != null) {
            setText(textDependency.get());
        }
        Dependency<Typeface> typefaceDependency = getTypefaceDependency();
        if (typefaceDependency != null) {
            setTypeface(typefaceDependency.get());
        }
        Dependency<Float> sizeDependency = getSizeDependency();
        if (sizeDependency != null) {
            setSize(sizeDependency.get().floatValue());
        }
    }
}
