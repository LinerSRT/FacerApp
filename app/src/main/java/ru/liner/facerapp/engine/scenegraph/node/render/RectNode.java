package ru.liner.facerapp.engine.scenegraph.node.render;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.canvas.instruction.AntiAliasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.RectInstruction;
import ru.liner.facerapp.engine.scenegraph.Alignment;


public class RectNode extends AlignedNode {
    private static final Alignment DEFAULT_ALIGNMENT = Alignment.TOP_LEFT;

    public RectNode(float width, float height) {
        super(width, height);
    }

    public RectNode(float width, float height, Alignment alignment) {
        super(width, height, alignment);
    }

    @Override 
    protected void computeContentInstructions(@NonNull List<CanvasRenderInstruction> instructions) {
        instructions.add(AntiAliasInstruction.INSTANCE);
        instructions.add(new RectInstruction(getWidth(), getHeight()));
    }

    @Override 
    protected Alignment getDefaultAlignment() {
        return DEFAULT_ALIGNMENT;
    }
}
