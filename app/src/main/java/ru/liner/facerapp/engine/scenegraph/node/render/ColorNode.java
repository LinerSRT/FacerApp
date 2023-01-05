package ru.liner.facerapp.engine.scenegraph.node.render;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.math.MathUtils;

import java.util.List;

import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ColorInstruction;
import ru.liner.facerapp.engine.bounds.Bound2D;


public class ColorNode extends PaintNode {
    public static final int ALPHA_MAX = 255;
    private int alpha;
    @ColorInt
    private int color;
    private ColorInstruction instruction;

    public ColorNode(@ColorInt int color) {
        this(color, 255);
    }

    public ColorNode(@ColorInt int color, int alpha) {
        this.instruction = null;
        this.color = color;
        setAlpha(alpha);
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
        setInstruction();
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        setInstruction();
    }

    protected void setInstruction() {
        this.instruction = new ColorInstruction(this.color, this.alpha);
    }

    public static int toAlphaInt(double alpha) {
        return (int) Math.floor(MathUtils.clamp(alpha * ALPHA_MAX, 0f, ALPHA_MAX));
    }

    @Override 
    protected void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        instructions.add(this.instruction);
    }

    @Override 
    public Bound2D getClickBound() {
        return null;
    }
}
