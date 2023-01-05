package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;


public class TranslateCanvasInstruction implements CanvasRenderInstruction {
    public static final TranslateCanvasInstruction ZERO = new TranslateCanvasInstruction(0.0f, 0.0f);
    private final float posX;
    private final float posY;

    public TranslateCanvasInstruction(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.translate(this.posX, this.posY);
    }
}
