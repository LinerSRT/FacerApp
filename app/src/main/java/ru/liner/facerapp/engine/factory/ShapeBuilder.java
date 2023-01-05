package ru.liner.facerapp.engine.factory;


import ru.liner.facerapp.engine.Shape;
import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.dependency.DependencySceneNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorNode;
import ru.liner.facerapp.engine.scenegraph.node.render.StyleNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyCircleNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyPolygonNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyRectNode;

/* loaded from: classes.dex */
public class ShapeBuilder extends NodeBuilder {
    private Alignment alignment;
    private ColorNode colorNode;
    private float height;
    private Dependency<Float> heightDependency;
    private float radius;
    private Dependency<Float> radiusDependency;
    private Shape shape;
    private StyleNode styleNode;

    public ShapeBuilder(Shape shape, float radius) {
        this.shape = shape;
        this.radius = radius;
    }

    public ShapeBuilder(Shape shape, float width, float height) {
        this.shape = shape;
        this.radius = width;
        this.height = height;
    }

    public ShapeBuilder(Shape shape, Dependency<Float> radiusDependency) {
        this.shape = shape;
        this.radiusDependency = radiusDependency;
    }

    public ShapeBuilder(Shape shape, Dependency<Float> widthDependency, Dependency<Float> heightDependency) {
        this.shape = shape;
        this.radiusDependency = widthDependency;
        this.heightDependency = heightDependency;
    }

    public ShapeBuilder setAlignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public ShapeBuilder setColorNode(ColorNode colorNode) {
        this.colorNode = colorNode;
        return this;
    }

    public ShapeBuilder setStyleNode(StyleNode styleNode) {
        this.styleNode = styleNode;
        return this;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.factory.NodeBuilder
    public boolean buildNodes() {
        DependencySceneNode shapeNode;
        if (this.shape == null) {
            this.shape = Shape.TRIANGLE;
        }
        if (this.colorNode == null) {
            this.colorNode = new ColorNode(-1);
        }
        attach(this.colorNode);
        if (this.styleNode == null) {
            this.styleNode = new StyleNode(StyleNode.DEFAULT_STYLE);
        }
        attach(this.styleNode);
        if (this.radiusDependency == null) {
            this.radiusDependency = new ConstantDependency(Float.valueOf(this.radius));
        }
        if (this.heightDependency == null) {
            this.heightDependency = new ConstantDependency(Float.valueOf(this.height));
        }
        if (Shape.RECTANGLE.equals(this.shape) || Shape.LINE.equals(this.shape)) {
            shapeNode = new DependencyRectNode(this.radiusDependency, this.heightDependency, this.alignment);
        } else if (Shape.CIRCLE.equals(this.shape)) {
            shapeNode = new DependencyCircleNode(this.radiusDependency);
        } else {
            shapeNode = new DependencyPolygonNode(this.shape, this.radiusDependency);
        }
        applyDependencies(shapeNode);
        attach(shapeNode);
        return true;
    }
}
