package ru.liner.facerapp.engine.factory;

import android.graphics.Bitmap;
import android.util.Log;

import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorFilterNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyBitmapNode;


public class BitmapBuilder extends NodeBuilder {
    public static final String TAG = BitmapBuilder.class.getSimpleName();
    private Alignment alignment;
    private ColorNode alphaNode;
    private Bitmap bitmap;
    private Dependency<Bitmap> bitmapDependency;
    private ColorFilterNode colorNode;
    private Dependency<Float> heighDependency;
    private float height;
    private float width;
    private Dependency<Float> widthDependency;

    public BitmapBuilder(Bitmap bitmap, float width, float height) {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
    }

    public BitmapBuilder(Dependency<Bitmap> bitmapDependency, Dependency<Float> widthDependency, Dependency<Float> heightDependency) {
        this.bitmapDependency = bitmapDependency;
        this.widthDependency = widthDependency;
        this.heighDependency = heightDependency;
    }

    public BitmapBuilder setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }

    public BitmapBuilder setBitmapDependency(Dependency<Bitmap> dependency) {
        this.bitmapDependency = dependency;
        return this;
    }

    public BitmapBuilder setWidth(float width) {
        this.width = width;
        return this;
    }

    public BitmapBuilder setWidthDependency(Dependency<Float> dependency) {
        this.widthDependency = dependency;
        return this;
    }

    public BitmapBuilder setHeight(float height) {
        this.height = height;
        return this;
    }

    public BitmapBuilder setHeightDependency(Dependency<Float> dependency) {
        this.heighDependency = dependency;
        return this;
    }

    public BitmapBuilder setAlignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public BitmapBuilder setAlpha(ColorNode alphaNode) {
        this.alphaNode = alphaNode;
        return this;
    }

    public BitmapBuilder setColorFilter(ColorFilterNode colorNode) {
        this.colorNode = colorNode;
        return this;
    }

    @Override 
    protected boolean buildNodes() {
        if ((this.bitmap == null || this.bitmap.isRecycled()) && this.bitmapDependency == null) {
            return false;
        }
        if (this.alphaNode == null) {
            this.alphaNode = new ColorNode(-1, 255);
        }
        attach(this.alphaNode);
        attach(this.colorNode);
        Log.d(TAG, "width = " + this.width + " height = " + this.height);
        DependencyBitmapNode bitmapNode = new DependencyBitmapNode(this.bitmapDependency, this.widthDependency, this.heighDependency, this.alignment);
        bitmapNode.setBitmap(this.bitmap);
        bitmapNode.setWidth(this.width);
        bitmapNode.setHeight(this.height);
        applyDependencies(bitmapNode);
        attach(bitmapNode);
        return true;
    }
}
