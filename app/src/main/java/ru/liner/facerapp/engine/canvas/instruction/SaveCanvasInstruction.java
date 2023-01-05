package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;


public class SaveCanvasInstruction implements CanvasRenderInstruction {
    public static final SaveCanvasInstruction INSTRUCTION = new SaveCanvasInstruction();

    protected SaveCanvasInstruction() {
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.save();
    }
}
