package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ColorInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PathInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PorterDuffXferInstruction;


public class ClipToCircleNode extends PaintNode {
    private static final PorterDuffXferInstruction xferInstruction = new PorterDuffXferInstruction(PorterDuff.Mode.SRC_ATOP);
    private Path path = null;

    public ClipToCircleNode(float diameter) {
        super(RenderPass.ALL);
        recalculate(diameter);
    }

    protected Path computePath(float width, float height) {
        Path path = new Path();
        path.addCircle(width / 2.0f, height / 2.0f, width / 2.0f, Path.Direction.CCW);
        path.close();
        return path;
    }

    public synchronized void recalculate(float diameter) {
        this.path = computePath(diameter, diameter);
    }

    @Override 
    protected synchronized void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        RenderEnvironment environment = RenderEnvironment.getInstance();
        if (environment != null && RenderEnvironment.ClipMode.ROUND.equals(environment.getClipMode())) {
            if (this.path != null && RenderPass.INITIALIZE.equals(renderPass)) {
                instructions.add(ColorInstruction.GREEN);
                instructions.add(new PathInstruction(this.path));
            }
            instructions.add(xferInstruction);
            environment.addDefaultPaintInstruction(xferInstruction);
        }
    }

    
    @Override 
    public void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        RenderEnvironment environment = RenderEnvironment.getInstance();
        if (environment != null) {
            environment.removeDefaultPaintInstruction(xferInstruction);
        }
        super.computePostTraversalRenderInstructions(instructions, renderPass);
    }

    @Override 
    public Bound2D getClickBound() {
        return null;
    }
}
