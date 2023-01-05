package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;


public class ScaleCanvasInstruction implements CanvasRenderInstruction {
    private final float scaleX;
    private final float scaleY;

    public ScaleCanvasInstruction(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.scale(this.scaleX, this.scaleY);
    }
}
