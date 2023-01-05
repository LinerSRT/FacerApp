package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;

public class ClearShadowLayerInstruction implements CanvasRenderInstruction {
    @Override
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.clearShadowLayer();
    }
}
