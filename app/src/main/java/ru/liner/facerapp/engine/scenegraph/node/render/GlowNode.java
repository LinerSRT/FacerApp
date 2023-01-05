package ru.liner.facerapp.engine.scenegraph.node.render;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ClearShadowLayerInstruction;
import ru.liner.facerapp.engine.canvas.instruction.GlowInstruction;

/* loaded from: classes.dex */
public class GlowNode extends PaintNode {
    @ColorInt
    private int color;
    private int size;

    public GlowNode(@ColorInt int color, int size) {
        this.color = color;
        this.size = size;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.MultiPassRenderableNode
    protected void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        instructions.add(new GlowInstruction(this.color, this.size));
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.PaintNode, com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.MultiPassRenderableNode
    public void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        instructions.add(new ClearShadowLayerInstruction());
        super.computePostTraversalRenderInstructions(instructions, renderPass);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.input.ClickBoundable
    public Bound2D getClickBound() {
        return null;
    }
}
