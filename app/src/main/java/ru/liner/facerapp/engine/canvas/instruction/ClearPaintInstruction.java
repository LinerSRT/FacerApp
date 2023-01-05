package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;

public class ClearPaintInstruction implements CanvasRenderInstruction {
    public static final ClearPaintInstruction INSTRUCTION = new ClearPaintInstruction();

    protected ClearPaintInstruction() {
    }

    @Override // com.jeremysteckling.facerrel.lib.renderer.skyfire.canvas.instruction.CanvasRenderInstruction
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.reset();
        paint.setColor(-1);
    }
}
