package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.AntiAliasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.FillStyleInstruction;
import ru.liner.facerapp.engine.canvas.instruction.StrokeStyleInstruction;


public class StyleNode extends PaintNode {
    public static final float DEFAULT_STROKE_WIDTH = 1.0f;
    public static final Paint.Style DEFAULT_STYLE = Paint.Style.FILL;
    private float strokeWidth;
    private Paint.Style style;

    public StyleNode(Paint.Style style) {
        this(style, 1.0f);
    }

    public StyleNode(Paint.Style style, float strokeWidth) {
        this.style = DEFAULT_STYLE;
        this.strokeWidth = 1.0f;
        this.style = style;
        this.strokeWidth = strokeWidth;
    }

    @Override 
    protected void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        Paint.Style style = this.style;
        if (style == null) {
            style = DEFAULT_STYLE;
        }
        instructions.add(AntiAliasInstruction.INSTANCE);
        if (style == Paint.Style.FILL) {
            instructions.add(FillStyleInstruction.INSTANCE);
        } else if (style == Paint.Style.STROKE) {
            instructions.add(new StrokeStyleInstruction(this.strokeWidth));
        }
    }

    @Override 
    public Bound2D getClickBound() {
        return null;
    }
}
