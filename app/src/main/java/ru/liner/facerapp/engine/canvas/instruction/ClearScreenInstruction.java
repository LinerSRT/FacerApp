package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.scenegraph.ClearMode;

public class ClearScreenInstruction implements CanvasRenderInstruction {
    private final ClearMode clearMode;
    @ColorInt
    private final int color;

    public ClearScreenInstruction(@NonNull ClearMode clearMode, @ColorInt int color) {
        this.clearMode = clearMode;
        this.color = color;
    }

    @Override
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        if (ClearMode.RGB_BUFFER.equals(this.clearMode)) {
            paint.setColor(this.color);
            canvas.drawPaint(paint);
        }
    }
}
