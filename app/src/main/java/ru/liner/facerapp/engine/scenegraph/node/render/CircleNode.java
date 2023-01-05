package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Path;

import ru.liner.facerapp.engine.Shape;
import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.bounds.CircleBound;


public class CircleNode extends PolygonNode {
    private Bound2D clickBounds;

    public CircleNode(float radius) {
        super(Shape.CIRCLE, radius);
    }

    @Override 
    protected synchronized Path computePath() {
        Path path;
        float radius = getRadius();
        path = new Path();
        path.addCircle(0.0f, 0.0f, radius, Path.Direction.CCW);
        path.close();
        return path;
    }

    @Override 
    public synchronized void setRadius(float radius) {
        super.setRadius(radius);
        recalculateClickBound();
    }

    protected void recalculateClickBound() {
        this.clickBounds = new CircleBound(0.0f, 0.0f, getRadius());
    }

    @Override 
    public Bound2D getClickBound() {
        return this.clickBounds;
    }
}
