package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.DrawableInstruction;
import ru.liner.facerapp.engine.scenegraph.Alignment;

/* loaded from: classes.dex */
public class DrawableNode extends AlignedNode {
    private static final Alignment DEFAULT_ALIGNMENT = Alignment.TOP_LEFT;
    private int alpha = 255;
    private ColorFilter colorFilter;
    private Drawable drawable;
    private DrawableInstruction instruction;

    public DrawableNode(Drawable drawable, float width, float height) {
        super(width, height);
        setDrawable(drawable);
    }

    public DrawableNode(Drawable drawable, float width, float height, Alignment alignment) {
        super(width, height, alignment);
        setDrawable(drawable);
    }

    public synchronized void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        precomputeInstructions();
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.AlignedNode
    public synchronized void setWidth(float width) {
        super.setWidth(width);
        precomputeInstructions();
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.AlignedNode
    public synchronized void setHeight(float height) {
        super.setHeight(height);
        precomputeInstructions();
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.AlignedNode
    public synchronized void setAlignment(Alignment alignment) {
        super.setAlignment(alignment);
        precomputeInstructions();
    }

    public synchronized void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
        precomputeInstructions();
    }

    public synchronized void setAlpha(int alpha) {
        this.alpha = alpha;
        precomputeInstructions();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.AlignedNode
    public void precomputeInstructions() {
        super.precomputeInstructions();
        if (this.drawable != null) {
            this.drawable.mutate().setColorFilter(this.colorFilter);
            this.drawable.mutate().setAlpha(this.alpha);
            this.instruction = new DrawableInstruction(this.drawable, getWidth(), getHeight());
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.AlignedNode
    protected synchronized void computeContentInstructions(@NonNull List<CanvasRenderInstruction> instructions) {
        if (this.drawable != null) {
            instructions.add(this.instruction);
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.node.render.AlignedNode
    protected Alignment getDefaultAlignment() {
        return DEFAULT_ALIGNMENT;
    }
}
