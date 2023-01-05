package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.bounds.RectBound;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.AntiAliasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ColorInstruction;
import ru.liner.facerapp.engine.canvas.instruction.FillStyleInstruction;
import ru.liner.facerapp.engine.canvas.instruction.RectInstruction;
import ru.liner.facerapp.engine.canvas.instruction.RenderTextInstruction;
import ru.liner.facerapp.engine.canvas.instruction.StrokeStyleInstruction;


public class TextNode extends MultiPassRenderableNode {
    private Paint.Align alignment;
    private final Rect boundRect;
    private RectInstruction debugBoundsInstruction;
    private RectInstruction debugOriginInstruction;
    private float size;
    private String text;
    private RenderTextInstruction textInstruction;
    private Typeface typeface;

    public TextNode(String text, Typeface typeface, float size, Paint.Align alignment) {
        this(text, typeface, size, alignment, RenderPass.DEFAULT);
    }

    public TextNode(String text, Typeface typeface, float size, Paint.Align alignment, @NonNull RenderPass renderPass) {
        super(renderPass);
        this.boundRect = new Rect();
        this.debugOriginInstruction = new RectInstruction(new Rect(-3, -3, 3, 3));
        this.text = text;
        this.typeface = typeface;
        this.size = size;
        this.alignment = alignment;
        precomputeInstructions();
    }

    public synchronized void setText(String text) {
        this.text = text;
        precomputeInstructions();
    }

    public synchronized void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        precomputeInstructions();
    }

    public synchronized void setSize(float size) {
        this.size = size;
        precomputeInstructions();
    }

    public synchronized void setAlignment(Paint.Align alignment) {
        this.alignment = alignment;
        precomputeInstructions();
    }

    protected synchronized void precomputeInstructions() {
        if (this.text == null || this.text.isEmpty() || this.typeface == null) {
            this.textInstruction = null;
        } else {
            this.textInstruction = new RenderTextInstruction(this.text, this.typeface, this.size, this.alignment);
        }
        precomputeTextBounds();
    }

    protected synchronized void precomputeTextBounds() {
        if (this.text == null || this.text.isEmpty() || this.typeface == null) {
            this.boundRect.set(0, 0, 0, 0);
        } else {
            Paint textPaint = new Paint();
            textPaint.setTextSize(this.size);
            textPaint.setTypeface(this.typeface);
            Rect textBounds = new Rect();
            textPaint.getTextBounds(this.text, 0, this.text.length(), textBounds);
            float halfWidth = ((float) textBounds.width()) / 2.0f;
            float height = ((float) textBounds.height()) / 2.0f;
            float left = 0.0f - halfWidth;
            float right = halfWidth;
            float top = (float) (-textBounds.height());
            if (Paint.Align.LEFT.equals(this.alignment)) {
                left = 0.0f;
                right = (float) textBounds.width();
            }
            if (Paint.Align.RIGHT.equals(this.alignment)) {
                left = (float) (-textBounds.width());
                right = 0.0f;
            }
            this.boundRect.set((int) left, (int) top, (int) right, (int) 0);
        }
        this.debugBoundsInstruction = new RectInstruction(this.boundRect);
    }

    @Override 
    protected synchronized void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        if (this.textInstruction != null) {
            instructions.add(AntiAliasInstruction.INSTANCE);
            instructions.add(this.textInstruction);
        }
        if (RenderPass.DEBUG.equals(renderPass)) {
            instructions.add(ColorInstruction.MAGENTA);
            instructions.add(StrokeStyleInstruction.DEBUG_STROKE);
            instructions.add(this.debugBoundsInstruction);
            instructions.add(FillStyleInstruction.INSTANCE);
            instructions.add(this.debugOriginInstruction);
        }
    }

    @Override 
    protected synchronized void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
    }

    @Override 
    public Bound2D getClickBound() {
        return new RectBound(this.boundRect);
    }
}
