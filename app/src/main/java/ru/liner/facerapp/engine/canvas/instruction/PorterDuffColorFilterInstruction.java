package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class PorterDuffColorFilterInstruction implements CanvasRenderInstruction {
    private final ColorFilter colorFilter;

    public PorterDuffColorFilterInstruction(@ColorInt int color, @NonNull PorterDuff.Mode mode) {
        this.colorFilter = new PorterDuffColorFilter(color, mode);
    }

    @Override
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.setColorFilter(this.colorFilter);
    }
}
