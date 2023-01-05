package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.PorterDuff;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ColorInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PathInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PorterDuffColorFilterInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PorterDuffXferInstruction;
/* loaded from: classes.dex */
public class ColorFilterNode extends PaintNode {
    public static final PorterDuff.Mode DEFAULT_FILTER_MODE = PorterDuff.Mode.MULTIPLY;
    @ColorInt
    private int color;
    private PorterDuff.Mode filterMode;

    public ColorFilterNode(@ColorInt int color) {
        this(color, DEFAULT_FILTER_MODE);
    }

    public ColorFilterNode(@ColorInt int color, PorterDuff.Mode filterMode) {
        this.color = color;
        setFilterMode(filterMode);
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    public void setFilterMode(PorterDuff.Mode filterMode) {
        this.filterMode = filterMode;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.MultiPassRenderableNode
    protected void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        if (this.filterMode != null) {
            instructions.add(new PorterDuffColorFilterInstruction(this.color, this.filterMode));
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.input.ClickBoundable
    public Bound2D getClickBound() {
        return null;
    }
}
