package ru.liner.facerapp.engine.scenegraph.node.render;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;


public class VisibilityNode extends MultiPassRenderableNode {
    private boolean isVisible;

    public VisibilityNode() {
        super(RenderPass.ALL, false);
        this.isVisible = true;
    }

    public VisibilityNode(boolean isVisible) {
        this();
        this.isVisible = isVisible;
    }

    public synchronized void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    protected synchronized boolean isVisible() {
        return this.isVisible;
    }

    
    @Override 
    public boolean shouldRender(@NonNull RenderPass renderPass) {
        return super.shouldRender(renderPass) && isVisible();
    }

    @Override 
    protected void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
    }

    @Override 
    protected void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
    }

    @Override 
    public Bound2D getClickBound() {
        return null;
    }
}
