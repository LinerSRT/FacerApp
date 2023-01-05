package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class GlowInstruction implements CanvasRenderInstruction {
    @ColorInt
    private final int color;
    private final int size;

    public GlowInstruction(@ColorInt int color, int size) {
        this.color = color;
        this.size = size;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.setShadowLayer((float) this.size, 0.0f, 0.0f, this.color);
    }
}
