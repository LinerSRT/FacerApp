package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;


public class StrokeStyleInstruction implements CanvasRenderInstruction {
    public static final StrokeStyleInstruction DEBUG_STROKE = new StrokeStyleInstruction(3.0f);
    private final float width;

    public StrokeStyleInstruction(float strokeWidth) {
        this.width = strokeWidth;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.width);
    }
}
