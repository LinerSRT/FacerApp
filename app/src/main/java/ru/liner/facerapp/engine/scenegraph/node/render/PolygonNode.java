package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Path;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.Shape;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.AntiAliasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ColorInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PathInstruction;
import ru.liner.facerapp.engine.canvas.instruction.StrokeStyleInstruction;
import ru.liner.facerapp.engine.bounds.Bound2D;


public class PolygonNode extends PaintNode {
    private Path path;
    private float radius;
    private final Shape shape;

    public PolygonNode(@NonNull Shape shape, float radius) {
        this(shape, radius, RenderPass.DEFAULT);
    }

    public PolygonNode(@NonNull Shape shape, float radius, @NonNull RenderPass renderPass) {
        super(renderPass);
        this.path = null;
        this.shape = shape;
        setRadius(radius);
    }

    public synchronized void setRadius(float radius) {
        this.radius = radius;
        this.path = computePath();
    }

    protected synchronized float getRadius() {
        return this.radius;
    }

    @Override 
    protected void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        if (this.path != null) {
            PathInstruction pathInstruction = new PathInstruction(this.path);
            instructions.add(AntiAliasInstruction.INSTANCE);
            instructions.add(pathInstruction);
            if (RenderPass.DEBUG.equals(renderPass)) {
                instructions.add(AntiAliasInstruction.INSTANCE);
                instructions.add(ColorInstruction.BLUE);
                instructions.add(StrokeStyleInstruction.DEBUG_STROKE);
                instructions.add(pathInstruction);
            }
        }
    }

    protected synchronized Path computePath() {
        Path path;
        path = new Path();
        int sides = this.shape.getNumSides();
        float segmentAngle = 6.2831855f / ((float) sides);
        float firstX = 0.0f;
        float firstY = 0.0f;
        for (int i = 1; i <= sides; i++) {
            float x = ((float) Math.sin((double) (((float) i) * segmentAngle))) * this.radius;
            float y = ((float) Math.cos((double) (((float) i) * segmentAngle))) * this.radius;
            if (i == 1) {
                path.moveTo(x, y);
                firstX = x;
                firstY = y;
            } else {
                path.lineTo(x, y);
            }
        }
        path.lineTo(firstX, firstY);
        path.close();
        return path;
    }

    @Override 
    public Bound2D getClickBound() {
        return null;
    }
}
