package ru.liner.facerapp.engine.scenegraph.node.render;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ClearPaintInstruction;


public abstract class PaintNode extends MultiPassRenderableNode {
    public PaintNode() {
        super(RenderPass.ALL);
    }

    public PaintNode(@NonNull RenderPass renderPass) {
        super(renderPass);
    }

    
    @Override 
    public void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        List<CanvasRenderInstruction> defaultPaintInstructions;
        instructions.add(ClearPaintInstruction.INSTRUCTION);
        RenderEnvironment environment = RenderEnvironment.getInstance();
        if (environment != null && (defaultPaintInstructions = environment.getDefaultPaintInstructions()) != null && !defaultPaintInstructions.isEmpty()) {
            instructions.addAll(defaultPaintInstructions);
        }
    }
}
