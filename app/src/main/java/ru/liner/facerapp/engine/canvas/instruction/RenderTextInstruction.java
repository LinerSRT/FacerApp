package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.annotation.NonNull;


public class RenderTextInstruction implements CanvasRenderInstruction {
    private final Paint.Align alignment;
    private final float size;
    private final String text;
    private final Typeface typeface;

    public RenderTextInstruction(String text, @NonNull Typeface typeface, float size, Paint.Align alignment) {
        this.text = text;
        this.alignment = alignment;
        this.typeface = typeface;
        this.size = size;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.setTextSize(this.size);
        paint.setTextAlign(this.alignment);
        paint.setTypeface(this.typeface);
        canvas.drawText(this.text, 0.0f, 0.0f, paint);
    }
}
