package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Rect;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ColorInstruction;
import ru.liner.facerapp.engine.canvas.instruction.FillStyleInstruction;
import ru.liner.facerapp.engine.canvas.instruction.RectInstruction;
import ru.liner.facerapp.engine.canvas.instruction.RestoreCanvasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.SaveCanvasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.StrokeStyleInstruction;
import ru.liner.facerapp.engine.canvas.instruction.TranslateCanvasInstruction;
import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.bounds.RectBound;
import ru.liner.facerapp.engine.scenegraph.Alignment;


public abstract class AlignedNode extends MultiPassRenderableNode {
    private Alignment alignment;
    protected final Rect boundRect;
    private RectInstruction debugBoundsInstruction;
    private RectInstruction debugOriginInstruction;
    private float height;
    private TranslateCanvasInstruction translateInstruction;
    private float width;

    protected abstract void computeContentInstructions(@NonNull List<CanvasRenderInstruction> list);

    protected abstract Alignment getDefaultAlignment();

    public AlignedNode(float width, float height) {
        this(width, height, null);
    }

    public AlignedNode(float width, float height, Alignment alignment) {
        this(width, height, alignment, RenderPass.DEFAULT);
    }

    public AlignedNode(float width, float height, Alignment alignment, @NonNull RenderPass renderPass) {
        super(renderPass);
        this.boundRect = new Rect();
        this.debugOriginInstruction = new RectInstruction(new Rect(-3, -3, 3, 3));
        this.width = width;
        this.height = height;
        this.alignment = alignment;
        precomputeInstructions();
    }

    public synchronized void setWidth(float width) {
        this.width = width;
        precomputeInstructions();
    }

    protected synchronized float getWidth() {
        return this.width;
    }

    public synchronized void setHeight(float height) {
        this.height = height;
        precomputeInstructions();
    }

    protected synchronized float getHeight() {
        return this.height;
    }

    public synchronized void setAlignment(Alignment alignment) {
        this.alignment = alignment;
        precomputeInstructions();
    }

    protected synchronized Alignment getAlignment() {
        return this.alignment;
    }

    
    public synchronized void precomputeInstructions() {
        float width = getWidth();
        float height = getHeight();
        Alignment alignment = getAlignment();
        if (alignment != null) {
            float xOffset = width * alignment.getAlignScalarX(getDefaultAlignment());
            float yOffset = height * alignment.getAlignScalarY(getDefaultAlignment());
            this.translateInstruction = new TranslateCanvasInstruction(xOffset, yOffset);
            this.boundRect.set((int) xOffset, (int) yOffset, (int) (xOffset + width), (int) (yOffset + height));
        } else {
            this.translateInstruction = null;
            this.boundRect.set(0, 0, (int) width, (int) height);
        }
        this.debugBoundsInstruction = new RectInstruction(this.boundRect);
    }

    @Override 
    protected synchronized void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        if (shouldRender(renderPass)) {
            TranslateCanvasInstruction translateInstruction = this.translateInstruction;
            if (translateInstruction != null) {
                instructions.add(SaveCanvasInstruction.INSTRUCTION);
                instructions.add(translateInstruction);
            }
            computeContentInstructions(instructions);
            if (translateInstruction != null) {
                instructions.add(RestoreCanvasInstruction.INSTRUCTION);
            }
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
    protected void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
    }

    @Override 
    public Bound2D getClickBound() {
        return new RectBound(this.boundRect);
    }
}
