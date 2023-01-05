package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.canvas.instruction.BitmapInstruction;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.scenegraph.Alignment;


public class BitmapNode extends AlignedNode {
    private static final Alignment DEFAULT_ALIGNMENT = Alignment.TOP_LEFT;
    private Bitmap bitmap;
    private Rect bitmapBounds;
    private BitmapInstruction instruction = null;

    public BitmapNode(Bitmap bitmap, float width, float height) {
        super(width, height);
        setBitmap(bitmap);
    }

    public BitmapNode(Bitmap bitmap, float width, float height, Alignment alignment) {
        super(width, height, alignment);
        setBitmap(bitmap);
    }

    public synchronized void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.bitmapBounds = new Rect();
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmapBounds.right = bitmap.getWidth();
            this.bitmapBounds.bottom = bitmap.getHeight();
        }
        setInstruction();
    }

    @Override 
    public synchronized void setWidth(float width) {
        super.setWidth(width);
        setInstruction();
    }

    @Override 
    public synchronized void setHeight(float height) {
        super.setHeight(height);
        setInstruction();
    }

    @Override 
    public synchronized void setAlignment(Alignment alignment) {
        super.setAlignment(alignment);
        setInstruction();
    }

    protected void setInstruction() {
        if (this.bitmap == null || this.bitmap.isRecycled()) {
            this.instruction = null;
            //Log.v(BitmapNode.class.getSimpleName(), "BitmapInstruction was null due to [" + (this.bitmap != null ? this.bitmap.isRecycled() ? "recycled" : "unknown/valid" : "null") + "] bitmap.");
            return;
        }
        this.instruction = new BitmapInstruction(this.bitmap, this.bitmapBounds, getWidth(), getHeight());
    }

    @Override 
    protected synchronized void computeContentInstructions(@NonNull List<CanvasRenderInstruction> instructions) {
        if (this.instruction != null) {
            instructions.add(this.instruction);
        }
    }

    @Override 
    protected Alignment getDefaultAlignment() {
        return DEFAULT_ALIGNMENT;
    }
}
