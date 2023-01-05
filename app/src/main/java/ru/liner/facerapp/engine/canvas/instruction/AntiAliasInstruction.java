package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;

public class AntiAliasInstruction implements CanvasRenderInstruction {
    public static final AntiAliasInstruction INSTANCE = new AntiAliasInstruction();

    protected AntiAliasInstruction() {
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.setAntiAlias(true);
    }
}
