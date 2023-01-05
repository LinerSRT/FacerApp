package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;


public class RotateCanvasInstruction implements CanvasRenderInstruction {
    private final float degrees;
    private final float pivotX;
    private final float pivotY;

    public RotateCanvasInstruction(float degrees, float pivotX, float pivotY) {
        this.degrees = degrees;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.rotate(this.degrees, this.pivotX, this.pivotY);
    }
}
