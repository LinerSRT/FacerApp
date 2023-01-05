package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;


public class RestoreCanvasInstruction implements CanvasRenderInstruction {
    public static final RestoreCanvasInstruction INSTRUCTION = new RestoreCanvasInstruction();

    protected RestoreCanvasInstruction() {
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.restore();
    }
}
