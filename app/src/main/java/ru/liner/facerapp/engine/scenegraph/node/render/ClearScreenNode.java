package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ClearScreenInstruction;
import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.scenegraph.ClearMode;


public class ClearScreenNode extends MultiPassRenderableNode {
    public static final int DEFAULT_CLEAR_COLOR = Color.parseColor("#FF00FF");
    private final ClearMode clearMode;
    private final int color;

    public ClearScreenNode(@NonNull ClearMode clearMode, @NonNull RenderPass renderPass) {
        this(clearMode, renderPass, DEFAULT_CLEAR_COLOR);
    }

    public ClearScreenNode(@NonNull ClearMode clearMode, @NonNull RenderPass renderPass, @ColorInt int color) {
        super(renderPass);
        this.clearMode = clearMode;
        this.color = color;
    }

    @Override 
    protected void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        if (ClearMode.RGB_BUFFER.equals(this.clearMode)) {
            instructions.add(new ClearScreenInstruction(this.clearMode, this.color));
        }
    }

    @Override 
    protected void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
    }

    @Override 
    public Bound2D getClickBound() {
        return null;
    }
}
