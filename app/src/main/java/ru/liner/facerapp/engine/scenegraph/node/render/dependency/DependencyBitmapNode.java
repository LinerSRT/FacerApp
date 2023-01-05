package ru.liner.facerapp.engine.scenegraph.node.render.dependency;

import android.graphics.Bitmap;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.BitmapNode;


public class DependencyBitmapNode extends BitmapNode {
    private Dependency<Bitmap> bitmapDependency = null;
    private Dependency<Float> widthDependency = null;
    private Dependency<Float> heightDependency = null;

    public DependencyBitmapNode(Dependency<Bitmap> bitmapDependency, Dependency<Float> widthDependency, Dependency<Float> heightDependency) {
        super(bitmapDependency.get(), 0.0f, 0.0f);
        setBitmapDependency(bitmapDependency);
        setWidthDependency(widthDependency);
        setHeightDependency(heightDependency);
    }

    public DependencyBitmapNode(Dependency<Bitmap> bitmapDependency, Dependency<Float> widthDependency, Dependency<Float> heightDependency, Alignment alignment) {
        super(bitmapDependency.get(), 0.0f, 0.0f, alignment);
        setBitmapDependency(bitmapDependency);
        setWidthDependency(widthDependency);
        setHeightDependency(heightDependency);
    }

    public synchronized void setBitmapDependency(Dependency<Bitmap> dependency) {
        this.bitmapDependency = dependency;
        if (this.bitmapDependency != null) {
            this.bitmapDependency.invalidate();
        }
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

    @Override 
    public synchronized void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.bitmapDependency != null) {
            this.bitmapDependency.update(currentTimeMillis);
            setBitmap(bitmapDependency.get());
        }
        if (this.widthDependency != null) {
            this.widthDependency.update(currentTimeMillis);
            setWidth(widthDependency.get());
        }
        if (this.heightDependency != null) {
            this.heightDependency.update(currentTimeMillis);
            setHeight(heightDependency.get());
        }
    }
}
