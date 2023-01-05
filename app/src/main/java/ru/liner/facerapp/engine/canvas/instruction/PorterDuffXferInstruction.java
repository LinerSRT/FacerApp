package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import androidx.annotation.NonNull;

public class PorterDuffXferInstruction implements CanvasRenderInstruction {
    private final Xfermode xfermode;

    public PorterDuffXferInstruction(@NonNull PorterDuff.Mode mode) {
        this.xfermode = new PorterDuffXfermode(mode);
    }

    @Override
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.setXfermode(this.xfermode);
    }
}
