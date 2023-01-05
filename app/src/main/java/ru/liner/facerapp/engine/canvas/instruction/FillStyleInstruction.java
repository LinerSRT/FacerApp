package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;

public class FillStyleInstruction implements CanvasRenderInstruction {
    public static final FillStyleInstruction INSTANCE = new FillStyleInstruction();

    protected FillStyleInstruction() {
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.setStyle(Paint.Style.FILL);
    }
}
