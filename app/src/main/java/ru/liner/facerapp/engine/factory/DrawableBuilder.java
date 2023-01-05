package ru.liner.facerapp.engine.factory;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyDrawableNode;

@Deprecated
/* loaded from: classes.dex */
public class DrawableBuilder extends NodeBuilder {
    public static final String TAG = DrawableBuilder.class.getSimpleName();
    private Alignment alignment;
    private int alpha = 255;
    private Dependency<Float> alphaDependency;
    private ColorFilter colorFilter;
    private Dependency<ColorFilter> colorFilterDependency;
    private Drawable drawable;
    private Dependency<Drawable> drawableDependency;
    private Dependency<Float> heighDependency;
    private float height;
    private float width;
    private Dependency<Float> widthDependency;

    public DrawableBuilder(Drawable drawable, float width, float height) {
        this.drawable = drawable;
        this.width = width;
        this.height = height;
    }

    public DrawableBuilder(Dependency<Drawable> drawableDependency, Dependency<Float> widthDependency, Dependency<Float> heightDependency) {
        this.drawableDependency = drawableDependency;
        this.widthDependency = widthDependency;
        this.heighDependency = heightDependency;
    }

    public DrawableBuilder setDrawable(Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    public DrawableBuilder setDrawableDependency(Dependency<Drawable> dependency) {
        this.drawableDependency = dependency;
        return this;
    }

    public DrawableBuilder setWidth(float width) {
        this.width = width;
        return this;
    }

    public DrawableBuilder setWidthDependency(Dependency<Float> dependency) {
        this.widthDependency = dependency;
        return this;
    }

    public DrawableBuilder setHeight(float height) {
        this.height = height;
        return this;
    }

    public DrawableBuilder setHeightDependency(Dependency<Float> dependency) {
        this.heighDependency = dependency;
        return this;
    }

    public DrawableBuilder setAlignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public DrawableBuilder setAlpha(int alpha) {
        this.alpha = alpha;
        return this;
    }

    public DrawableBuilder setAlphaDependency(Dependency<Float> dependency) {
        this.alphaDependency = dependency;
        return this;
    }

    public DrawableBuilder setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
        return this;
    }

    public DrawableBuilder setColorFilterDependency(Dependency<ColorFilter> dependency) {
        this.colorFilterDependency = dependency;
        return this;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.factory.NodeBuilder
    protected boolean buildNodes() {
        if (this.drawable == null && this.drawableDependency == null) {
            return false;
        }
        DependencyDrawableNode drawableNode = new DependencyDrawableNode(this.drawableDependency, this.widthDependency, this.heighDependency, this.alignment);
        drawableNode.setDrawable(this.drawable);
        drawableNode.setWidth(this.width);
        drawableNode.setHeight(this.height);
        drawableNode.setColorFilterDependency(this.colorFilterDependency);
        drawableNode.setColorFilter(this.colorFilter);
        drawableNode.setAlphaDependency(this.alphaDependency);
        drawableNode.setAlpha(this.alpha);
        applyDependencies(drawableNode);
        attach(drawableNode);
        return true;
    }
}
