package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;

public interface CanvasRenderInstruction {
    void render(@NonNull Canvas canvas, @NonNull Paint paint);
}
