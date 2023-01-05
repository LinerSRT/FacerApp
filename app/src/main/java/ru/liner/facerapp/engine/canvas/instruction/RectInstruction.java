package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.NonNull;


public class RectInstruction implements CanvasRenderInstruction {
    private final RectF rect = new RectF();

    public RectInstruction(float width, float height) {
        this.rect.set(0.0f, 0.0f, width, height);
    }

    public RectInstruction(@NonNull Rect srcRect) {
        this.rect.set((float) srcRect.left, (float) srcRect.top, (float) srcRect.right, (float) srcRect.bottom);
    }

    protected RectF getRect() {
        return this.rect;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.drawRect(this.rect, paint);
    }
}
