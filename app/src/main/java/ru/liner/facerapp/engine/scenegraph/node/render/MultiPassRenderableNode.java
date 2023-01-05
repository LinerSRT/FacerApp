package ru.liner.facerapp.engine.scenegraph.node.render;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.scenegraph.node.GraphNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public abstract class MultiPassRenderableNode extends RenderableNode {
    private RenderPass drawPass;
    private boolean shouldDrawThrough;

    protected abstract void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> list, @NonNull RenderPass renderPass);

    protected abstract void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> list, @NonNull RenderPass renderPass);

    public MultiPassRenderableNode(RenderPass renderPass) {
        this(renderPass, true);
    }

    public MultiPassRenderableNode(RenderPass renderPass, boolean shouldDrawThrough) {
        this.drawPass = RenderPass.DEFAULT;
        this.shouldDrawThrough = true;
        if (renderPass != null) {
            this.drawPass = renderPass;
        }
        this.shouldDrawThrough = shouldDrawThrough;
    }

    public void setDrawPass(RenderPass renderPass) {
        if (renderPass == null) {
            this.drawPass = RenderPass.DEFAULT;
        } else {
            this.drawPass = renderPass;
        }
    }

    @Override 
    public void computeRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions) {
        computeRenderPass(instructions, RenderPass.INITIALIZE);
        computeRenderPass(instructions, RenderPass.PRE);
        computeRenderPass(instructions, RenderPass.DEFAULT);
        computeRenderPass(instructions, RenderPass.TRANSPARENCY);
        computeRenderPass(instructions, RenderPass.POST);
        computeRenderPass(instructions, RenderPass.FINAL);
    }


    public boolean shouldRender(@NonNull RenderPass renderPass) {
        return renderPass.equals(this.drawPass) || RenderPass.ALL.equals(this.drawPass);
    }

    protected void computeRenderPass(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        List<GraphNode> children;
        if (shouldRender(renderPass) || RenderPass.DEBUG.equals(renderPass)) {
            computePreTraversalRenderInstructions(instructions, renderPass);
        }
        if ((shouldRender(renderPass) || this.shouldDrawThrough || RenderPass.DEBUG.equals(renderPass)) && (children = getChildren()) != null && !children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                GraphNode child = children.get(i);
                if (child instanceof MultiPassRenderableNode) {
                    ((MultiPassRenderableNode) child).computeRenderPass(instructions, renderPass);
                } else if ((child instanceof RenderableNode) && RenderPass.DEFAULT.equals(renderPass)) {
                    ((RenderableNode) child).computeRenderInstructions(instructions);
                }
            }
        }
        if (shouldRender(renderPass) || RenderPass.DEBUG.equals(renderPass)) {
            computePostTraversalRenderInstructions(instructions, renderPass);
        }
    }

    @Override 
    protected final void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions) {
    }

    @Override 
    protected final void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions) {
    }
}